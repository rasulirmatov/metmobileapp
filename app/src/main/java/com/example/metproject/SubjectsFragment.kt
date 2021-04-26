package com.example.metproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.metproject.adapters.SubjectsFragmentCardAdapter
import com.example.metproject.databinding.FragmentSubjectsBinding
import com.example.metproject.models.SubjectsFragmentCardModel
import com.example.metproject.models.response.ResponseClassesModel
import com.example.metproject.models.response.ResponseSubjectsModel
import com.example.metproject.viewModels.ClassesFragmentViewModel
import com.example.metproject.viewModels.SubjectsFragmentViewModel
import com.google.android.material.transition.MaterialFadeThrough
import kotlinx.android.synthetic.main.fragment_subjects.*




class SubjectsFragment : BaseFragment() {

    private lateinit var binding: FragmentSubjectsBinding

    private val viewModel: SubjectsFragmentViewModel by lazy {
        ViewModelProviders.of(this).get(SubjectsFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentSubjectsBinding>(
            inflater,
            R.layout.fragment_subjects,
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showProgressDialog()

        val llm = GridLayoutManager(requireContext(), 2)
        binding.rv.layoutManager = llm

        binding.subjectsToolbar.setNavigationIcon(R.drawable.ic_icon_go_to_back)
        binding.subjectsToolbar.setNavigationOnClickListener(View.OnClickListener {
            if (!binding.searchView.isIconified) {
                binding.searchView.isIconified = true
                binding.searchView.clearFocus()
            } else {
                requireActivity().onBackPressed()
            }
        })

        checkNetworkandLoadData()

        binding.swiperefresh.setOnRefreshListener {
            binding.rvContainer.visibility = View.GONE
            checkNetworkandLoadData()
        }

        binding.btnReload.setOnClickListener{
            showProgressDialog()
            binding.networkProblemLayout.visibility = View.GONE
            checkNetworkandLoadData()
        }

    }

    fun checkNetworkandLoadData(){
        if (isNetworkAvailable()){
            makeApiCall()
            binding.networkProblemLayout.visibility = View.GONE
        } else {
            binding.swiperefresh.isRefreshing = false
            hideProgressDialog()
            binding.connectivityTextMessage.text = "Пайвастшавӣ бо интернет гум!"
            binding.animationView.setAnimation(R.raw.anim_no_internet)
            binding.networkProblemLayout.visibility = View.VISIBLE
            binding.rvContainer.visibility = View.GONE
        }
    }

    fun makeApiCall(): SubjectsFragmentViewModel {
        viewModel.getRecyclerListDataObserver()
            .observe(viewLifecycleOwner, Observer<ResponseSubjectsModel> {
                swiperefresh.isRefreshing = false
                if (it != null) {
                    //update the adapter
                    viewModel.setAdapterData(it.data.toMutableList())
                    hideProgressDialog()
                    binding.rvContainer.visibility = View.VISIBLE
                } else {
                    showProgressDialog()
                    binding.rvContainer.visibility = View.GONE
                    binding.connectivityTextMessage.text = "Корҳои техники дар система :("
                    binding.animationView.setAnimation(R.raw.anim_technical_problem)
                    binding.networkProblemLayout.visibility = View.VISIBLE
                    hideProgressDialog()
                }
            })
        viewModel.makeAPICall("", requireContext())
        return viewModel
    }

}