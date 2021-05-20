package com.app.maktabielektroni

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.app.maktabielektroni.databinding.FragmentClassesBinding
import com.app.maktabielektroni.models.response.ResponseClassesModel
import com.app.maktabielektroni.viewModels.ClassesFragmentViewModel
import kotlinx.android.synthetic.main.fragment_classes.*


class ClassesFragment : BaseFragment() {

    private lateinit var binding: FragmentClassesBinding

    private val viewModel: ClassesFragmentViewModel by lazy {
        ViewModelProviders.of(this).get(ClassesFragmentViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentClassesBinding>(
            inflater,
            R.layout.fragment_classes,
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

    override fun onDestroy() {
        super.onDestroy()
        hideProgressDialog()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        showProgressDialog()

        binding.classesToolbar.setNavigationIcon(R.drawable.ic_icon_go_to_back)
        binding.classesToolbar.setNavigationOnClickListener(View.OnClickListener {
            requireActivity().onBackPressed()
        })
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvClasses.layoutManager = gridLayoutManager

        checkNetworkandLoadData()

        binding.swiperefresh.setOnRefreshListener {
            binding.rvContainer.visibility = GONE
            checkNetworkandLoadData()
        }

        binding.btnReload.setOnClickListener {
            showProgressDialog()
            binding.networkProblemLayout.visibility = GONE
            checkNetworkandLoadData()
        }

    }

    fun checkNetworkandLoadData() {
        if (isNetworkAvailable()) {
            showProgressDialog()
            makeApiCall()
            binding.networkProblemLayout.visibility = GONE
        } else {
            binding.swiperefresh.isRefreshing = false
            hideProgressDialog()
            binding.connectivityTextMessage.text = "Пайвастшавӣ бо интернет гум!"
            binding.animationView.setAnimation(R.raw.anim_no_internet)
            binding.networkProblemLayout.visibility = VISIBLE
            binding.rvContainer.visibility = GONE
        }
    }

    fun makeApiCall(): ClassesFragmentViewModel {
        viewModel.getRecyclerListDataObserver()
            .observe(viewLifecycleOwner, Observer<ResponseClassesModel> {
                hideProgressDialog()
                swiperefresh.isRefreshing = false
                if (it != null) {
                    //update the adapter
                    viewModel.setAdapterData(it.data.toMutableList())
                    hideProgressDialog()
                    binding.rvContainer.visibility = VISIBLE
                } else {
                    showProgressDialog()
                    binding.rvContainer.visibility = GONE
                    binding.connectivityTextMessage.text = "Корҳои техники дар система :("
                    binding.animationView.setAnimation(R.raw.anim_technical_problem)
                    binding.networkProblemLayout.visibility = VISIBLE
                    hideProgressDialog()
                }
            })
        viewModel.makeAPICall("", requireContext())
        return viewModel
    }

}