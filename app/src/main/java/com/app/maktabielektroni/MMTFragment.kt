package com.app.maktabielektroni

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.app.maktabielektroni.databinding.FragmentMMTBinding
import com.app.maktabielektroni.models.response.ResponseClusters
import com.app.maktabielektroni.viewModels.MMTFragmentViewModel
import kotlinx.android.synthetic.main.fragment_m_m_t.*

class MMTFragment : BaseFragment() {
    private lateinit var binding: FragmentMMTBinding

    private val viewModel: MMTFragmentViewModel by lazy {
        ViewModelProviders.of(this).get(MMTFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentMMTBinding>(
            inflater,
            R.layout.fragment_m_m_t,
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
        binding.mmtToolbar.setNavigationIcon(R.drawable.ic_icon_go_to_back)
        binding.mmtToolbar.setNavigationOnClickListener(View.OnClickListener {
            requireActivity().onBackPressed()
        })
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvMmt.layoutManager = gridLayoutManager

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

    }

    fun checkNetworkandLoadData() {
        if (isNetworkAvailable()) {
            showProgressDialog()
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

    fun makeApiCall(): MMTFragmentViewModel {
        viewModel.getRecyclerListDataObserver()
            .observe(viewLifecycleOwner, Observer<ResponseClusters> {
                hideProgressDialog()
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