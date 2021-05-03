package com.example.metproject

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.example.metproject.databinding.FragmentExerciseBinding
import com.example.metproject.databinding.FragmentPdfViewerBinding

class ExerciseFragment : BaseFragment() {
    private lateinit var binding: FragmentExerciseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentExerciseBinding>(
            inflater,
            R.layout.fragment_exercise,
            container,
            false
        )
        dataBinding.lifecycleOwner = this
        binding = dataBinding
        setHasOptionsMenu(true)
        return dataBinding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.exerciseToolbar.setNavigationIcon(R.drawable.ic_icon_go_to_back)
        binding.exerciseToolbar.setNavigationOnClickListener(View.OnClickListener {
            requireActivity().onBackPressed()
        })

        binding.webViewExercise.webViewClient = WebViewClient()
        binding.webViewExercise.settings.displayZoomControls = false
        binding.webViewExercise.settings.builtInZoomControls = false
        binding.webViewExercise.settings.javaScriptEnabled = true
        binding.webViewExercise.loadData(
            "<html><body>" + arguments?.getString("get_exercise").toString() + "</body></html>",
            "text/html", "UTF-8"
        );
    }


}