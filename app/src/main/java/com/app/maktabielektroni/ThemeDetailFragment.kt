package com.app.maktabielektroni

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.transition.TransitionInflater
import com.app.maktabielektroni.databinding.FragmentThemeDetailBinding
import com.app.maktabielektroni.models.response.ResponseThemeDetail
import com.app.maktabielektroni.utils.Constants
import com.app.maktabielektroni.viewModels.ThemeDetailFragmentViewModel
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.rajat.pdfviewer.PdfViewerActivity
import kotlinx.android.synthetic.main.fragment_theme_detail.*


class ThemeDetailFragment : BaseFragment(), Player.EventListener {

    private lateinit var binding: FragmentThemeDetailBinding
    private var show_more_btn_clicked = false
    private var bundle: Bundle = Bundle()


    private lateinit var simpleExoplayer: SimpleExoPlayer

    private val viewModel: ThemeDetailFragmentViewModel by lazy {
        ViewModelProviders.of(this).get(ThemeDetailFragmentViewModel::class.java)
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
        val dataBinding = DataBindingUtil.inflate<FragmentThemeDetailBinding>(
            inflater,
            R.layout.fragment_theme_detail,
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

        binding.themeName.isSelected = true
        binding.themesToolbar.inflateMenu(R.menu.menu_theme_detail)
        binding.themesToolbar.setOnMenuItemClickListener {
            shareIntent(Constants.share_theme + theme_id)
            true
        }


        checkNetworkandLoadData()

        binding.btnThemeKonspekt.setOnClickListener {
            if (pdf_src != null) {
                activity?.let {
                    it.startActivity(
                        PdfViewerActivity.launchPdfFromUrl(           //PdfViewerActivity.Companion.launchPdfFromUrl(..   :: incase of JAVA
                            context,
                            pdf_src,                                // PDF URL in String format
                            "Конспект",                        // PDF Name/Title in String format
                            "",                  // If nothing specific, Put "" it will save to Downloads
                            enableDownload = false                    // This param is true by defualt.
                        )
                    )
                }
            } else {
                showToast("Конспект ҳоло вуҷуд надорад !")
            }
        }

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
                        .navigate(R.id.action_themeDetailFragment_to_exerciseFragment,bundle)
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

    fun makeApiCall(): ThemeDetailFragmentViewModel {
        viewModel.getRecyclerListDataObserver()
            .observe(viewLifecycleOwner, Observer<ResponseThemeDetail> {
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
                        videoView(Constants.video_path + it.data.videoSrc)
                        video_src = (Constants.video_path + it.data.videoSrc)
                        pdf_src = (Constants.pdf_path + it.data.pdfSrc)
                        if (it.data.pdfExercise.contains(".pdf", ignoreCase = true)) {
                            pdf_exercise = Constants.pdf_path+it.data.pdfExercise
                            is_pdf_src = true
                        }else {
                            pdf_exercise = it.data.pdfExercise
                            is_pdf_src = false
                            bundle.putString("get_exercise",pdf_exercise)
                        }
                        if (it.data.pdfSrc.isEmpty()) pdf_src = null
                        bundle.putString("pdf_src", pdf_src)
                        hide_show_items("show_all")
                        binding.themeName.text = ("Мавзӯи " + it.data.themeNum + " " + it.data.name)
                        removeHtmlTags(it.data.introduction)?.let { it1 -> description(it1) }
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
            arguments?.getString("theme_id").toString(),
            requireContext()
        )
        return viewModel
    }

    fun hide_show_items(for_what: String) {
        when (for_what) {
            "rotation_landscape" -> {
                peshguftor_card.visibility = View.GONE
                btn_theme_exercises.visibility = View.GONE
                btn_theme_konspekt.visibility = View.GONE
                btn_theme_tests.visibility = View.GONE
                topAppBar.visibility = View.GONE
                exoplayerViewContainer.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
                requireActivity().window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                );
            }
            "rotation_portrait" -> {
                peshguftor_card.visibility = View.VISIBLE
                btn_theme_exercises.visibility = View.VISIBLE
                btn_theme_konspekt.visibility = View.VISIBLE
//                btn_theme_tests.visibility = View.VISIBLE
                topAppBar.visibility = View.VISIBLE
                exoplayerViewContainer.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    770
                )
                requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            }
            "show_more_btn_click" -> {
                show_more_btn_clicked = true
                exoplayerViewContainer.visibility = View.GONE
                btn_theme_exercises.visibility = View.GONE
                btn_theme_konspekt.visibility = View.GONE
                btn_theme_tests.visibility = View.GONE
                show_more_btn.visibility = View.GONE
                expand_arrow.setImageResource(R.drawable.ic_x)

                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
                peshguftor_text.ellipsize = null
                peshguftor_text.maxLines = Integer.MAX_VALUE
                peshguftor_card.layoutParams = params
            }
            "x_btn_click" -> {
                if (show_more_btn_clicked) {
                    show_more_btn_clicked = false
                    exoplayerViewContainer.visibility = View.VISIBLE
                    expand_arrow.setImageResource(R.drawable.ic_expand_arrow)
                    expand_arrow.rotation = 0f
                    btn_theme_exercises.visibility = View.VISIBLE
                    btn_theme_konspekt.visibility = View.VISIBLE
//                    btn_theme_tests.visibility = View.VISIBLE

                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    peshguftor_card.layoutParams = params
                }
            }
            "hide_all" -> {
                binding.exoplayerViewContainer.visibility = View.GONE
                binding.componentsContainer.visibility = View.GONE
            }
            "show_all" -> {
                binding.exoplayerViewContainer.visibility = View.VISIBLE
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

    override fun onStop() {
        super.onStop()
        exoplayerView.player?.stop()
    }

    fun videoView(url: String) {
        var isPortrait = true
        // VideoPlayer
        simpleExoplayer = SimpleExoPlayer.Builder(requireContext()).build()
        exoplayerView.player = simpleExoplayer
        exoplayerView.useController = false

        // Build the media item.
        val mediaItem: MediaItem = MediaItem.fromUri(url)

        // Set the media item to be played.
        simpleExoplayer.setMediaItem(mediaItem)

        exoplayerView.controllerShowTimeoutMs = 0
        exoplayerView.controllerAutoShow = false

        exoplayerView.setOnClickListener {
            if (!exoplayerView.isControllerFullyVisible) {
                Handler().removeCallbacksAndMessages(null)
                fullscreen_btn.visibility = View.VISIBLE
                Log.d("playerview", exoplayerView.isControllerFullyVisible.toString())
            } else {
                fullscreen_btn.visibility = View.INVISIBLE
                Log.d("playerview", exoplayerView.isControllerFullyVisible.toString())
            }
            exoplayerView.setShowNextButton(false)
            exoplayerView.setShowPreviousButton(false)
        }

        fullscreen_btn.setOnClickListener {
            showToast("BtnClicked")
            if (isPortrait) {
                requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                fullscreen_btn.setImageResource(R.drawable.exo_controls_fullscreen_exit)
                hide_show_items("rotation_landscape")
            } else {
                requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                fullscreen_btn.setImageResource(R.drawable.exo_controls_fullscreen_enter)
                hide_show_items("rotation_portrait")
            }
            isPortrait = !isPortrait
        }

        play_btn.setOnClickListener {
            simpleExoplayer.prepare()
            simpleExoplayer.play()
            play_btn.visibility = View.GONE
            exoplayerView.useController = true
        }

    }

    fun description(description: String) {
        peshguftor_card.setOnClickListener {
            if (!description_visibility) {
                var params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
                peshguftor_card.layoutParams = params
                expand_arrow.rotation = 180f
                peshguftor_decor_line.visibility = View.VISIBLE
                peshguftor_text.visibility = View.VISIBLE
                peshguftor_text.text.length
                peshguftor_text.viewTreeObserver
                    .addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            binding.peshguftorText.text = description
                            if (binding.peshguftorText.lineCount > 5) {
                                binding.peshguftorText.viewTreeObserver.removeOnGlobalLayoutListener(
                                    this
                                )
                                binding.peshguftorText.ellipsize = TextUtils.TruncateAt.END
                                binding.peshguftorText.maxLines = 5
                                binding.showMoreBtn.visibility = View.VISIBLE
                            } else if (binding.peshguftorText.lineCount == 1) {
                                binding.showMoreBtn.visibility = View.GONE
                                binding.peshguftorText.ellipsize = null
                                binding.peshguftorText.maxLines = Integer.MAX_VALUE
                                params = LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                                )
                                binding.peshguftorCard.layoutParams = params
                            } else {
                                binding.showMoreBtn.visibility = View.GONE
                                binding.peshguftorText.ellipsize = null
                                binding.peshguftorText.maxLines = Integer.MAX_VALUE
                            }

                        }
                    })
                description_visibility = true
            }
            expand_arrow.setOnClickListener {
                if (!description_visibility) {
                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                    binding.peshguftorCard.layoutParams = params
                    expand_arrow.rotation = 180f
                    peshguftor_decor_line.visibility = View.VISIBLE
                    binding.peshguftorText.visibility = View.VISIBLE
                    binding.peshguftorText.text.length
                    binding.peshguftorText.viewTreeObserver
                        .addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                            override fun onGlobalLayout() {
                                binding.peshguftorText.text = description
                                if (binding.peshguftorText.lineCount > 5) {
                                    binding.peshguftorText.viewTreeObserver.removeOnGlobalLayoutListener(
                                        this
                                    )
                                    binding.peshguftorText.ellipsize = TextUtils.TruncateAt.END
                                    binding.peshguftorText.maxLines = 5
                                    binding.showMoreBtn.visibility = View.VISIBLE
                                } else {
                                    binding.showMoreBtn.visibility = View.GONE
                                    binding.peshguftorText.ellipsize = null
                                    binding.peshguftorText.maxLines = Integer.MAX_VALUE
                                }
                            }
                        })
                    description_visibility = true
                } else {
                    hide_show_items("x_btn_click")
                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    peshguftor_card.layoutParams = params
                    expand_arrow.rotation = 0f
                    peshguftor_decor_line.visibility = View.GONE
                    binding.peshguftorText.visibility = View.GONE
                    binding.showMoreBtn.visibility = View.GONE
                    description_visibility = false
                    exoplayerView.visibility = View.VISIBLE
                    btn_theme_exercises.visibility = View.VISIBLE
                    btn_theme_konspekt.visibility = View.VISIBLE
//                    btn_theme_tests.visibility = View.VISIBLE
                }
            }

            show_more_btn.setOnClickListener {
                hide_show_items("show_more_btn_click")
            }

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.slide_right)
        enterTransition = inflater.inflateTransition(R.transition.slide_left)
    }

}