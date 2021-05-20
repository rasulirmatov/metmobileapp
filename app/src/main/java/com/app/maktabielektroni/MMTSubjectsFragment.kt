package com.app.maktabielektroni

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.app.maktabielektroni.adapters.MMTSubjectsFragmentAdapter
import com.app.maktabielektroni.databinding.FragmentMMTSubjectsBinding
import com.app.maktabielektroni.models.response.ResponseSubjectsByClusterId
import com.app.maktabielektroni.viewModels.MMTSubjectsFragmentViewModel

class MMTSubjectsFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMMTSubjectsBinding

    private val viewModel: MMTSubjectsFragmentViewModel by lazy {
        ViewModelProviders.of(this).get(MMTSubjectsFragmentViewModel::class.java)
    }

    private lateinit var recyclerViewAdapter: MMTSubjectsFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentMMTSubjectsBinding>(
            inflater,
            R.layout.fragment_m_m_t_subjects,
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

        val gridLayoutManager = GridLayoutManager(this.context,2)
        binding.rvBottomsheet.layoutManager = gridLayoutManager

        Log.i("ArgumentBundle",arguments?.get("cluster_id").toString())

        if (isNetworkAvailable()) {
            makeApiCall(arguments?.get("cluster_id").toString())
        } else {
            binding.resultEmptyLayout.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }

    }

    private fun makeApiCall(cluster_id: String?): MMTSubjectsFragmentViewModel {
        viewModel.getRecyclerListDataObserver()
            .observe(viewLifecycleOwner, Observer<ResponseSubjectsByClusterId> {
                binding.progressBar.visibility = View.GONE
                if (it != null) {
                    //update the adapter
                    if (it.status == "400" || it.data.equals("")){
                        binding.resultEmptyLayout.visibility = View.VISIBLE
                    }else {
                        recyclerViewAdapter = MMTSubjectsFragmentAdapter { s: String, s1: String ->
                            itemClick(s,s1)
                        }
                        recyclerViewAdapter.setDataList(it.data.mmts.toMutableList())
                        binding.rvBottomsheet.adapter = recyclerViewAdapter
                        recyclerViewAdapter.notifyDataSetChanged()
                        binding.rvBottomsheet.visibility = View.VISIBLE
                    }
                } else {
                    binding.resultEmptyLayout.visibility = View.VISIBLE
                }
            })
        viewModel.makeAPICall(cluster_id, requireContext())
        return viewModel
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    private fun itemClick(mmt_fan_id: String,component_name: String) {
        val bundle = Bundle()
        bundle.putString("mmt_fan_id", mmt_fan_id)
        bundle.putString("component_name", component_name)
        parentFragment?.findNavController()
            ?.navigate(R.id.action_MMTSubjectListFragment_to_MMTDetailFragment, bundle)
    }
}