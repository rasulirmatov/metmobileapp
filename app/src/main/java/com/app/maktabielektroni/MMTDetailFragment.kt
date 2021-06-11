package com.app.maktabielektroni

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.transition.TransitionInflater
import com.app.maktabielektroni.databinding.FragmentMMTDetailBinding
import com.app.maktabielektroni.databinding.FragmentOlympiadDetailBinding
import com.app.maktabielektroni.models.response.ResponseMMTdetail
import com.app.maktabielektroni.models.response.ResponseOlympicDetail
import com.app.maktabielektroni.utils.Constants
import com.app.maktabielektroni.viewModels.MMTDetailFragmentViewModel
import com.app.maktabielektroni.viewModels.OlympiadDetailFragmentViewModel
import com.google.android.material.transition.MaterialFadeThrough
import com.rajat.pdfviewer.PdfViewerActivity


class MMTDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentMMTDetailBinding
    private var show_more_btn_clicked = false
    private var bundle: Bundle = Bundle()

    private val viewModel: MMTDetailFragmentViewModel by lazy {
        ViewModelProviders.of(this).get(MMTDetailFragmentViewModel::class.java)
    }

    private var pdf_src: String? = null
    private var video_src: String? = null
    private var pdf_exercise: String? = null
    private var is_pdf_src = false

    private var peshguftor: String? = null
    private var theme_id: String? = null
    var description_visibility = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentMMTDetailBinding>(
            inflater,
            R.layout.fragment_m_m_t_detail,
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
        // Toolbar
        binding.themesToolbar.setNavigationIcon(R.drawable.ic_icon_go_to_back)
        binding.themesToolbar.setNavigationOnClickListener(View.OnClickListener {
            requireActivity().onBackPressed()
        })

        checkNetworkandLoadData()

        binding.btnThemeExercises.setOnClickListener {
            if (pdf_exercise != null) {
                if (is_pdf_src == true) {
                    activity?.let {
                        it.startActivity(
                            PdfViewerActivity.launchPdfFromUrl(           //PdfViewerActivity.Companion.launchPdfFromUrl(..   :: incase of JAVA
                                context,
                                pdf_exercise,                                // PDF URL in String format
                                "Масъалаҳо бо ҳал",                        // PDF Name/Title in String format
                                "",                  // If nothing specific, Put "" it will save to Downloads
                                enableDownload = false                    // This param is true by defualt.
                            )
                        )
                    }
                } else {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_MMTDetailFragment_to_exerciseFragment,bundle)
                }

            } else {
                showToast("Супориш ҳоло вуҷуд надорад !")
            }
        }

        binding.btnReload.setOnClickListener {
            showProgressDialog()
            binding.networkProblemLayout.visibility = View.GONE
            checkNetworkandLoadData()
        }

    }

    fun checkNetworkandLoadData() {
        showProgressDialog()
        if (isNetworkAvailable()) {
            makeApiCall()
            hide_show_items("hide_no_internet_connection")
        } else {
            hideProgressDialog()
            hide_show_items("show_no_internet_connection")
        }
    }

    fun makeApiCall(): MMTDetailFragmentViewModel {
        viewModel.getRecyclerListDataObserver()
            .observe(viewLifecycleOwner, Observer<ResponseMMTdetail> {
                if (it != null) {
                    if (it.status == "400") {
                        showProgressDialog()
                        hide_show_items("show_technical_problems")
                        hide_show_items("hide_all")
                        hideProgressDialog()
                    } else if (it.data == null) {
                        binding.resultEmptyLayout.visibility = View.VISIBLE
                        hide_show_items("hide_technical_problems")
                        hide_show_items("hide_all")
                        hideProgressDialog()
                    } else {
                        hideProgressDialog()
                        pdf_src = (Constants.pdf_path + it.data.pdfSrc)
                        if (!it.data.pdfSrc.isNullOrEmpty() && it.data.pdfSrc.contains(".pdf", ignoreCase = true)) {
                            pdf_exercise = Constants.pdf_path+it.data.pdfSrc
                            is_pdf_src = true
                        }else {
                            pdf_exercise = it.data.pdfSrc
                            is_pdf_src = false
                            bundle.putString("get_exercise",pdf_exercise)
                        }
                        if (it.data.pdfSrc.isNullOrEmpty()) pdf_src = null
                        bundle.putString("pdf_src", pdf_src)
                        hide_show_items("show_all")
                        binding.themeName.text = (arguments?.get("component_name").toString())
                        binding.themeName.isSelected = true
                        theme_id = it.data.id.toString()
                    }
                } else {
                    showProgressDialog()
                    hide_show_items("show_technical_problems")
                    hideProgressDialog()
                }
            })
        viewModel.makeAPICall(
            arguments?.get("mmt_fan_id").toString(),
            requireContext()
        )
        return viewModel
    }

    fun hide_show_items(for_what: String) {
        when (for_what) {
            "hide_all" -> {
                binding.componentsContainer.visibility = View.GONE
            }
            "show_all" -> {
                binding.componentsContainer.visibility = View.VISIBLE
            }
            "show_no_internet_connection" -> {
                binding.connectivityTextMessage.text = "Пайвастшавӣ бо интернет гум!"
                binding.animationView.setAnimation(R.raw.anim_no_internet)
                binding.networkProblemLayout.visibility = View.VISIBLE
            }
            "hide_no_internet_connection" -> {
                binding.networkProblemLayout.visibility = View.GONE
            }
            "show_empty_layout" -> {
                binding.resultEmptyLayout.visibility = View.VISIBLE
            }
            "hide_empty_layout" -> {
                binding.resultEmptyLayout.visibility = View.GONE
            }
            "show_technical_problems" -> {
                binding.connectivityTextMessage.text = "Корҳои техники дар система :("
                binding.animationView.setAnimation(R.raw.anim_technical_problem)
                binding.networkProblemLayout.visibility = View.VISIBLE
            }
            "hide_technical_problems" -> {
                binding.networkProblemLayout.visibility = View.GONE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
//        exitTransition = inflater.inflateTransition(R.transition.slide_right)
//        enterTransition = inflater.inflateTransition(R.transition.slide_left)
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()
    }


}