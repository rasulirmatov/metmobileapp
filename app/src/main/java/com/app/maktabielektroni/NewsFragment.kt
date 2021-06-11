package com.app.maktabielektroni

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.app.maktabielektroni.adapters.NewsFragmentAdapter
import com.app.maktabielektroni.databinding.FragmentNewsBinding
import com.app.maktabielektroni.models.response.ResponseNews
import com.app.maktabielektroni.viewModels.MainFragmentViewModel
import com.google.android.material.transition.MaterialFadeThrough

class NewsFragment : BaseFragment() {

    private lateinit var binding: FragmentNewsBinding
    private lateinit var adapter: NewsFragmentAdapter

    private val viewModel: MainFragmentViewModel by lazy {
        ViewModelProviders.of(this).get(MainFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentNewsBinding>(
            inflater,
            R.layout.fragment_news,
            container,
            false
        )
        dataBinding.lifecycleOwner = this
        binding = dataBinding

        setHasOptionsMenu(true)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showProgressDialog()

        val llm = GridLayoutManager(requireContext(), 1)
        binding.rv.layoutManager = llm

        binding.newsToolbar.setNavigationIcon(R.drawable.ic_icon_go_to_back)
        binding.newsToolbar.setNavigationOnClickListener(View.OnClickListener {
            requireActivity().onBackPressed()
        })

        checkNetworkandLoadData()

//        binding.swiperefresh.setOnRefreshListener {
//            binding.rvContainer.visibility = View.GONE
//            checkNetworkandLoadData()
//        }

        binding.btnReload.setOnClickListener {
            showProgressDialog()
            binding.networkProblemLayout.visibility = View.GONE
            checkNetworkandLoadData()
        }

    }

    fun checkNetworkandLoadData() {
        if (isNetworkAvailable()) {
            makeApiCall()
            binding.networkProblemLayout.visibility = View.GONE
        } else {
//            binding.swiperefresh.isRefreshing = false
            hideProgressDialog()
            binding.connectivityTextMessage.text = "Пайвастшавӣ бо интернет гум!"
            binding.animationView.setAnimation(R.raw.anim_no_internet)
            binding.networkProblemLayout.visibility = View.VISIBLE
            binding.rvContainer.visibility = View.GONE
        }
    }

    fun makeApiCall(): MainFragmentViewModel {
        viewModel.getRecyclerListDataObserver()
            .observe(viewLifecycleOwner, Observer<ResponseNews> {
                hideProgressDialog()
//                swiperefresh.isRefreshing = false
                if (it != null) {
                    //update the adapter
                    adapter =
                        NewsFragmentAdapter(it.data.toMutableList())
                    binding.rv.adapter = adapter
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
        viewModel.makeAPICall(requireContext())
        return viewModel

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
//        exitTransition = inflater.inflateTransition(R.transition.slide_up)
        enterTransition = inflater.inflateTransition(R.transition.slide_up)
//        enterTransition = MaterialFadeThrough()
//        exitTransition = MaterialFadeThrough()
    }


}