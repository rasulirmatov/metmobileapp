package com.example.metproject

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.ParcelFileDescriptor
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.metproject.databinding.FragmentOlympiadDetailBinding
import com.example.metproject.models.response.ResponseOlympicDetail
import com.example.metproject.models.response.ResponseThemeDetail
import com.example.metproject.utils.Constants
import com.example.metproject.viewModels.OlympiadDetailFragmentViewModel
import com.example.metproject.viewModels.ThemeDetailFragmentViewModel
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.rajat.pdfviewer.PdfViewerActivity
import kotlinx.android.synthetic.main.fragment_theme_detail.*


class OlympiadDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentOlympiadDetailBinding
    private var show_more_btn_clicked = false
    private var bundle: Bundle = Bundle()

    private val viewModel: OlympiadDetailFragmentViewModel by lazy {
        ViewModelProviders.of(this).get(OlympiadDetailFragmentViewModel::class.java)
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
        val dataBinding = DataBindingUtil.inflate<FragmentOlympiadDetailBinding>(
            inflater,
            R.layout.fragment_olympiad_detail,
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
                                "Супориш",                        // PDF Name/Title in String format
                                "",                  // If nothing specific, Put "" it will save to Downloads
                                enableDownload = false                    // This param is true by defualt.
                            )
                        )
                    }
                } else {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_olympiadDetailFragment_to_exerciseFragment,bundle)
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

    fun makeApiCall(): OlympiadDetailFragmentViewModel {
        viewModel.getRecyclerListDataObserver()
            .observe(viewLifecycleOwner, Observer<ResponseOlympicDetail> {
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
                        binding.themeName.text = ("Олимпиадаи " + arguments?.get("subject_name").toString() + ". СИНФИ" + arguments?.get("class_number").toString())
                        peshguftor = removeHtmlTags(it.data.introduction)
                        theme_id = it.data.id.toString()
                    }
                } else {
                    showProgressDialog()
                    hide_show_items("show_technical_problems")
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


}