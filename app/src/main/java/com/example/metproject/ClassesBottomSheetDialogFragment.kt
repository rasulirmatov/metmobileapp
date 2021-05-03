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
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.metproject.adapters.ClassesFragmentBottomSheetDialogCardAdapter
import com.example.metproject.databinding.FragmentClassesBottomSheetDialogBinding
import com.example.metproject.models.response.ResponseClassesBySubject
import com.example.metproject.viewModels.ClassesFragmentBottomSheetDialogViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ClassesBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentClassesBottomSheetDialogBinding

    private lateinit var recyclerViewAdapter: ClassesFragmentBottomSheetDialogCardAdapter

    private val viewModel: ClassesFragmentBottomSheetDialogViewModel by lazy {
        ViewModelProviders.of(this).get(ClassesFragmentBottomSheetDialogViewModel()::class.java)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentClassesBottomSheetDialogBinding>(
            inflater,
            R.layout.fragment_classes_bottom_sheet_dialog,
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

//        val offsetFromTop = 200
//        (dialog as? BottomSheetDialog)?.behavior?.apply {
//            isFitToContents = true
//            setExpandedOffset(offsetFromTop)
//            isDraggable = true
//            state = BottomSheetBehavior.STATE_DRAGGING
//        }


        val gridLayoutManager = GridLayoutManager(this.context, 2)
        binding.rvBottomsheet.layoutManager = gridLayoutManager

        Log.i("ArgumentBundle", arguments?.get("subject_id").toString())

        if (isNetworkAvailable()) {
            makeApiCall(arguments?.get("subject_id").toString())
        } else {
            binding.resultEmptyLayout.visibility = VISIBLE
            binding.progressBar.visibility = GONE
        }

//        binding.animationView.setOnClickListener{
//            parentFragment?.findNavController()?.navigate(R.id.action_classesBottomSheetDialogFragment_to_themeFragmentList)
//        }


    }

    private fun makeApiCall(subjects_id: String?): ClassesFragmentBottomSheetDialogViewModel {
        viewModel.getRecyclerListDataObserver()
            .observe(viewLifecycleOwner, Observer<ResponseClassesBySubject> {
                binding.progressBar.visibility = GONE
                if (it != null) {
                    //update the adapter
                    if (it.status == "400" || it.data.isNullOrEmpty()) {
                        binding.resultEmptyLayout.visibility = VISIBLE
                    } else {
                        recyclerViewAdapter = ClassesFragmentBottomSheetDialogCardAdapter {
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
        viewModel.makeAPICall(subjects_id, requireContext())
        return viewModel
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    private fun itemClick(class_id: String) {
        val bundle = Bundle()
        bundle.putString("selected_subject", arguments?.get("subject_id").toString())
        bundle.putString("selected_class", class_id)
        parentFragment?.findNavController()
            ?.navigate(R.id.action_classesBottomSheetDialogFragment_to_themeFragmentList, bundle)
    }


}

