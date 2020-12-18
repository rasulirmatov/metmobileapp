package com.example.metproject

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

class SplashScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        requireActivity().window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(),R.color.white);// set status background white
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