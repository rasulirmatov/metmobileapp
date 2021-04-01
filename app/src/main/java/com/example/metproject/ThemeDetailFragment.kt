package com.example.metproject

import android.content.pm.ActivityInfo
import android.content.res.Configuration
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
import androidx.navigation.Navigation
import com.example.metproject.databinding.FragmentThemeDetailBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.android.synthetic.main.fragment_theme_detail.*


class ThemeDetailFragment : BaseFragment(), Player.EventListener {

    private lateinit var binding: FragmentThemeDetailBinding
    private var show_more_btn_clicked = false

    private lateinit var simpleExoplayer: SimpleExoPlayer
    private val mp4Url =
        "https://video.resh.edu.ru/uploads/stream/40/3040/720/720p.m3u8"

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
            shareIntent("https://google.com")
            true
        }

        var isPortrait = true


        // VideoPlayer
        simpleExoplayer = SimpleExoPlayer.Builder(requireContext()).build()
        exoplayerView.player = simpleExoplayer
        exoplayerView.useController = false
        // Build the media item.
        val mediaItem: MediaItem = MediaItem.fromUri(mp4Url)
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

        var description_visibility = false

        peshguftor_card.setOnClickListener {
            if (!description_visibility) {
                val params = LinearLayout.LayoutParams(
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
                            if (peshguftor_text.lineCount > 5) {
                                peshguftor_text.viewTreeObserver.removeOnGlobalLayoutListener(this)
                                peshguftor_text.ellipsize = TextUtils.TruncateAt.END
                                peshguftor_text.maxLines = 5
                                show_more_btn.visibility = View.VISIBLE
                            } else {
                                show_more_btn.visibility = View.GONE
                                peshguftor_text.ellipsize = null
                                peshguftor_text.maxLines = Integer.MAX_VALUE
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
                    peshguftor_card.layoutParams = params
                    expand_arrow.rotation = 180f
                    peshguftor_decor_line.visibility = View.VISIBLE
                    peshguftor_text.visibility = View.VISIBLE
                    peshguftor_text.text.length

                    peshguftor_text.viewTreeObserver
                        .addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                            override fun onGlobalLayout() {
                                if (peshguftor_text.lineCount > 5) {
                                    peshguftor_text.viewTreeObserver.removeOnGlobalLayoutListener(
                                        this
                                    )
                                    peshguftor_text.ellipsize = TextUtils.TruncateAt.END
                                    peshguftor_text.maxLines = 5
                                    show_more_btn.visibility = View.VISIBLE
                                } else {
                                    show_more_btn.visibility = View.GONE
                                    peshguftor_text.ellipsize = null
                                    peshguftor_text.maxLines = Integer.MAX_VALUE
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
                    peshguftor_text.visibility = View.GONE
                    show_more_btn.visibility = View.GONE
                    description_visibility = false
                    exoplayerView.visibility = View.VISIBLE
                    btn_theme_exercises.visibility = View.VISIBLE
                    btn_theme_konspekt.visibility = View.VISIBLE
                    btn_theme_tests.visibility = View.VISIBLE
                }
            }

            show_more_btn.setOnClickListener{
                hide_show_items("show_more_btn_click")
            }

        }

        btn_theme_konspekt.setOnClickListener{
            Navigation.findNavController(it)
                .navigate(R.id.action_themeDetailFragment_to_pdfViewerFragment)
        }



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
                btn_theme_tests.visibility = View.VISIBLE
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
                peshguftor_card.layoutParams = params

            }
            "x_btn_click" -> {
                if (show_more_btn_clicked){
                    show_more_btn_clicked = false
                    exoplayerViewContainer.visibility = View.VISIBLE
                    expand_arrow.setImageResource(R.drawable.ic_expand_arrow)
                    expand_arrow.rotation = 0f
                btn_theme_exercises.visibility = View.VISIBLE
                btn_theme_konspekt.visibility = View.VISIBLE
                btn_theme_tests.visibility = View.VISIBLE

                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                peshguftor_card.layoutParams = params
                }

            }

        }

    }

    override fun onResume() {
        super.onResume()
//        showToast("resume")
    }

    override fun onStart() {
        super.onStart()
//        showToast("start")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        showToast("oncreate")
    }

    override fun onPause() {
        super.onPause()
//        showToast("onPause")
    }

    override fun onStop() {
        super.onStop()
//        showToast("onStop")
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

    }

}