package com.app.maktabielektroni

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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.app.maktabielektroni.adapters.SubjectsFragmentBottomSheetDialogCardAdapter
//import com.example.metproject.adapters.SubjectsFragmentBottomSheetDialogCardAdapter
import com.app.maktabielektroni.databinding.FragmentClassesBottomSheetDialogBinding
//import com.example.metproject.databinding.FragmentSuBottomSheetDialogBinding
import com.app.maktabielektroni.databinding.FragmentSubjectsBottomSheetDialogBinding
import com.app.maktabielektroni.models.response.ResponseSubjectsByClass
import com.app.maktabielektroni.viewModels.SubjectsFragmentBottomSheetDialogViewModel
//import com.example.metproject.models.ClassesFragmentCardModel
//import com.example.metproject.models.SubjectsFragmentCardModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SubjectsBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentSubjectsBottomSheetDialogBinding

    private val viewModel: SubjectsFragmentBottomSheetDialogViewModel by lazy {
        ViewModelProviders.of(this).get(SubjectsFragmentBottomSheetDialogViewModel::class.java)
    }

    private lateinit var recyclerViewAdapter: SubjectsFragmentBottomSheetDialogCardAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentSubjectsBottomSheetDialogBinding>(
            inflater,
            R.layout.fragment_subjects_bottom_sheet_dialog,
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

    private fun makeApiCall(class_id: String?): SubjectsFragmentBottomSheetDialogViewModel {
        viewModel.getRecyclerListDataObserver()
            .observe(viewLifecycleOwner, Observer<ResponseSubjectsByClass> { it ->
                binding.progressBar.visibility = GONE
                if (it != null) {
                    //update the adapter
                    if (it.status == "400" || it.data.isNullOrEmpty()){
                        binding.resultEmptyLayout.visibility = VISIBLE
                    }else {
                        recyclerViewAdapter = SubjectsFragmentBottomSheetDialogCardAdapter {
                            itemClick(it)
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

    private fun itemClick(subject_id: String) {
        val bundle = Bundle()
        bundle.putString("selected_class", arguments?.get("class_id").toString())
        bundle.putString("selected_subject", subject_id)
        parentFragment?.findNavController()
            ?.navigate(R.id.action_subjectsBottomSheetDialogFragment_to_themeFragmentList, bundle)
    }

}
