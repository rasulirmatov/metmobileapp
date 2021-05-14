package com.example.metproject

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.metproject.adapters.ClassesFragmentBottomSheetDialogCardAdapter
import com.example.metproject.adapters.OlympiadSubjectsFragmentAdapter
import com.example.metproject.adapters.SubjectsFragmentBottomSheetDialogCardAdapter
//import com.example.metproject.adapters.SubjectsFragmentBottomSheetDialogCardAdapter
import com.example.metproject.databinding.FragmentClassesBottomSheetDialogBinding
import com.example.metproject.databinding.FragmentOlympiadSubjectsBinding
//import com.example.metproject.databinding.FragmentSuBottomSheetDialogBinding
import com.example.metproject.databinding.FragmentSubjectsBottomSheetDialogBinding
import com.example.metproject.models.response.*
import com.example.metproject.utils.SuccessResponse
import com.example.metproject.viewModels.ClassesFragmentBottomSheetDialogViewModel
import com.example.metproject.viewModels.ClassesFragmentViewModel
import com.example.metproject.viewModels.OlympiadSubjectsFragmentViewModel
import com.example.metproject.viewModels.SubjectsFragmentBottomSheetDialogViewModel
//import com.example.metproject.models.ClassesFragmentCardModel
//import com.example.metproject.models.SubjectsFragmentCardModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_classes.*

class OlympiadSubjectsFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentOlympiadSubjectsBinding

    private val viewModel: OlympiadSubjectsFragmentViewModel by lazy {
        ViewModelProviders.of(this).get(OlympiadSubjectsFragmentViewModel::class.java)
    }

    private lateinit var recyclerViewAdapter: OlympiadSubjectsFragmentAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentOlympiadSubjectsBinding>(
            inflater,
            R.layout.fragment_olympiad_subjects,
            container,
            false
        )
        dataBinding.lifecycleOwner = this
        binding = dataBinding
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
        setHasOptionsMenu(true)
        return dataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridLayoutManager = GridLayoutManager(this.context,2)
        binding.rvBottomsheet.layoutManager = gridLayoutManager

        Log.i("ArgumentBundle",arguments?.get("sinf_id").toString())

        if (isNetworkAvailable()) {
            makeApiCall(arguments?.get("class_id").toString())
        } else {
            binding.resultEmptyLayout.visibility = VISIBLE
            binding.progressBar.visibility = GONE
        }


    }

    private fun makeApiCall(class_id: String?): OlympiadSubjectsFragmentViewModel {
        viewModel.getRecyclerListDataObserver()
            .observe(viewLifecycleOwner, Observer<ResponseOlympicSubjects> {
                binding.progressBar.visibility = GONE
                if (it != null) {
                    //update the adapter
                    if (it.status == 400 || it.data.isNullOrEmpty()){
                        binding.resultEmptyLayout.visibility = VISIBLE
                    }else {
                        recyclerViewAdapter = OlympiadSubjectsFragmentAdapter { s: String, s1: String ->
                            itemClick(s,s1)
                        }
                        recyclerViewAdapter.setDataList(it.data.toMutableList())
                        binding.rvBottomsheet.adapter = recyclerViewAdapter
                        recyclerViewAdapter.notifyDataSetChanged()
                        binding.rvBottomsheet.visibility = VISIBLE
                    }
                } else {
                    binding.resultEmptyLayout.visibility = VISIBLE
                }
            })
        viewModel.makeAPICall(class_id, requireContext())
        return viewModel
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    private fun itemClick(subject_id: String,subject_name: String) {
        val bundle = Bundle()
        bundle.putString("selected_class", arguments?.get("class_id").toString())
        bundle.putString("selected_subject", subject_id)
        bundle.putString("class_number", arguments?.get("class_number").toString())
        bundle.putString("subject_name", subject_name)
        parentFragment?.findNavController()
            ?.navigate(R.id.action_olympiadSubjectsFragment_to_olympiadDetailFragment, bundle)
    }

}
