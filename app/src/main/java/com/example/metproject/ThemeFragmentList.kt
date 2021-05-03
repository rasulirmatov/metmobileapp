package com.example.metproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.metproject.adapters.SubjectsFragmentCardAdapter
import com.example.metproject.adapters.ThemeFragmentListAdapter
import com.example.metproject.databinding.FragmentSubjectsBinding
import com.example.metproject.databinding.FragmentThemeListBinding
import com.example.metproject.models.SubjectsFragmentCardModel
import com.example.metproject.models.ThemeFragmentListModel
import com.example.metproject.models.response.ResponseClassesModel
import com.example.metproject.models.response.ResponseThemesModel
import com.example.metproject.viewModels.ClassesFragmentViewModel
import com.example.metproject.viewModels.ThemeFragmentListViewModel
import kotlinx.android.synthetic.main.fragment_classes.*
import kotlinx.android.synthetic.main.fragment_theme_list.*

class ThemeFragmentList : BaseFragment() {

    private lateinit var binding: FragmentThemeListBinding

    private val viewModel: ThemeFragmentListViewModel by lazy {
        ViewModelProviders.of(this).get(ThemeFragmentListViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentThemeListBinding>(
            inflater,
            R.layout.fragment_theme_list,
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

        val llm = LinearLayoutManager(this.context)
        binding.rvThemes.layoutManager = llm
        binding.rvThemes.addItemDecoration(
            DividerItemDecoration(
                binding.rvThemes.context,
                DividerItemDecoration.VERTICAL
            )
        )


        binding.themesToolbar.setNavigationIcon(R.drawable.ic_icon_go_to_back)
        binding.themesToolbar.setNavigationOnClickListener(View.OnClickListener {
            requireActivity().onBackPressed()
        })

        checkNetworkandLoadData()

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

    fun makeApiCall(): ThemeFragmentListViewModel {
        viewModel.getRecyclerListDataObserver()
            .observe(viewLifecycleOwner, Observer<ResponseThemesModel> {
//                swiperefresh.isRefreshing = false
                if (it != null) {
                    if (it.status == "400") {
                        showProgressDialog()
                        binding.rvContainer.visibility = View.GONE
                        binding.connectivityTextMessage.text = "Корҳои техники дар система :("
                        binding.animationView.setAnimation(R.raw.anim_technical_problem)
                        binding.networkProblemLayout.visibility = View.VISIBLE
                        hideProgressDialog()
                    } else if (it.data == null || it.data.themes.isEmpty()) {
                        binding.resultEmptyLayout.visibility = View.VISIBLE
                        binding.rvContainer.visibility = View.GONE
                        binding.networkProblemLayout.visibility = View.GONE
                        hideProgressDialog()
                    } else {
                        viewModel.setAdapterData(it.data.themes.toMutableList())
                        binding.classValue.text = it.data.sinf.classX
                        binding.subjectName.text = it.data.subject.name
                        hideProgressDialog()
                        binding.rvContainer.visibility = View.VISIBLE
                    }
                } else {
                    showProgressDialog()
                    binding.rvContainer.visibility = View.GONE
                    binding.connectivityTextMessage.text = "Корҳои техники дар система :("
                    binding.animationView.setAnimation(R.raw.anim_technical_problem)
                    binding.networkProblemLayout.visibility = View.VISIBLE
                    hideProgressDialog()
                }
            })
        viewModel.makeAPICall(
            arguments?.get("selected_class").toString(),
            arguments?.get("selected_subject").toString(),
            requireContext()
        )
        return viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        exitTransition = MaterialFadeThrough()
//        enterTransition = MaterialFadeThrough()
    }

}