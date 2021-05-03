package com.example.metproject

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_splash_screen.*

class SplashScreenFragment : Fragment() {

    lateinit var preferences: SharedPreferences

    val pref_show_sign_in = "Sign_In"

    val pref_show_intro = "Intro"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.slide_right)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val animationRotate = AnimationUtils.loadAnimation(context, R.anim.zoom_out)
        splashscreen_img.startAnimation(animationRotate)
//        requireActivity().window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(),R.color.white);// set status background white

//        preferences =
//            this.requireActivity().getSharedPreferences("SignIn", Context.MODE_PRIVATE)
        preferences =
            this.requireActivity().getSharedPreferences("IntroSlider", Context.MODE_PRIVATE)

        if (!preferences.getBoolean(pref_show_intro, true)) {
            Handler().postDelayed({
                findNavController()
                    .navigate(
                        R.id.action_splashScreenFragment_to_home,
                        null,
                        getNavOptions()
                    )
            }, 2000)
        } else {
            Handler().postDelayed({
                findNavController()
                    .navigate(
                        R.id.action_splashScreenFragment_to_introSliderFragment,
                        null,
                        NavOptions.Builder()
                            .setPopUpTo(
                                R.id.splashScreenFragment,
                                true
                            ).build()
                    )
            }, 2000)
        }
    }

    private fun getNavOptions(): NavOptions {
        return NavOptions.Builder()
//            .setEnterAnim(R.anim.slide_in)
//            .setExitAnim(R.anim.fade_out)
//            .setPopEnterAnim(R.anim.fade_in)
//            .setPopExitAnim(R.anim.slide_out)
            .setPopUpTo(
                R.id.splashScreenFragment,
                true
            )
            .build()
    }

}