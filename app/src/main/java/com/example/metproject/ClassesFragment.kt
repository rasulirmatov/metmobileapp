package com.example.metproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.metproject.adapters.ClassesFragmentCardAdapter
import com.example.metproject.databinding.FragmentClassesBinding
import com.example.metproject.models.response.ClassesItem
import com.example.metproject.models.response.ResponseClassesModel
import com.example.metproject.network.ApiService
import com.example.metproject.network.RetrofitClient
import com.example.metproject.utils.Constants
import com.example.metproject.viewModels.ClassesFragmentViewModel
import com.wessam.library.LayoutImage
import com.wessam.library.NoInternetLayout
import kotlinx.android.synthetic.main.fragment_classes.*
import okhttp3.CacheControl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showProgressDialog()

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