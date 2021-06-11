package com.app.maktabielektroni

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.transition.TransitionInflater
import com.app.maktabielektroni.databinding.FragmentWebViewerBinding
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.android.synthetic.main.fragment_web_viewer.*
import java.lang.Exception


class WebViewerFragment : BaseFragment() {
    private lateinit var binding: FragmentWebViewerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentWebViewerBinding>(
            inflater,
            R.layout.fragment_web_viewer,
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
        showProgressDialog()
        binding.webViewerToolbar.setNavigationIcon(R.drawable.ic_icon_go_to_back)
        binding.webViewerToolbar.setNavigationOnClickListener(View.OnClickListener {
            requireActivity().onBackPressed()
        })
        binding.titleName.text = arguments?.getString("title").toString()

        binding.webViewer.webViewClient = WebViewClient()
        binding.webViewer.settings.displayZoomControls = false
        binding.webViewer.settings.builtInZoomControls = false
        binding.webViewer.clearCache(false)
        binding.webViewer.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                showProgressDialog()
            }

            override fun onPageFinished(view: WebView, url: String) {
                val url =
                    "javascript:(function() {" + "document.querySelector(\".header-four\").remove();" +
                            "" +
                            "document.getElementById(\"footer-part\").remove();})()"
                try {
                    binding.webViewer.loadUrl(url)
                }catch (e: Exception){
                    Log.e("Error on WebVieweFragment", e.message)
                }
                hideProgressDialog()
                binding.webViewer.visibility = View.VISIBLE
            }

            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
                hideProgressDialog()
                Toast.makeText(
                    requireContext(),
                    "Ошибка подключения !$description",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        binding.webViewer.settings.javaScriptEnabled = true
        binding.webViewer.loadUrl(arguments?.getString("url").toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
//        exitTransition = inflater.inflateTransition(R.transition.slide_right)
//        enterTransition = inflater.inflateTransition(R.transition.slide_left)
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()
//        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ true)
//        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ false)
//        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ true)

    }

}