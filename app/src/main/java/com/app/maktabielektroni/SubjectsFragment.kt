package com.app.maktabielektroni

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.app.maktabielektroni.adapters.SubjectsFragmentCardAdapter
import com.app.maktabielektroni.databinding.FragmentSubjectsBinding
import com.app.maktabielektroni.models.response.ResponseSubjectsModel
import com.app.maktabielektroni.viewModels.SubjectsFragmentViewModel
import kotlinx.android.synthetic.main.fragment_subjects.*


class SubjectsFragment : BaseFragment() {

    private lateinit var binding: FragmentSubjectsBinding

    private lateinit var recyclerViewAdapter: SubjectsFragmentCardAdapter

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

        binding.btnReload.setOnClickListener {
            showProgressDialog()
            binding.networkProblemLayout.visibility = View.GONE
            checkNetworkandLoadData()
        }

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                recyclerViewAdapter.filter.filter(newText)
                return false
            }

        })

}

fun checkNetworkandLoadData() {
    if (isNetworkAvailable()) {
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
                recyclerViewAdapter = SubjectsFragmentCardAdapter(it.data.toMutableList())
                binding.rv.adapter = recyclerViewAdapter
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