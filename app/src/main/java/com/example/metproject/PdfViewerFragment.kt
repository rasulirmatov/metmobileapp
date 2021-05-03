package com.example.metproject

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.metproject.databinding.FragmentPdfViewerBinding
import kotlinx.android.synthetic.main.fragment_pdf_viewer.*


class PdfViewerFragment : BaseFragment() {
    private lateinit var binding: FragmentPdfViewerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentPdfViewerBinding>(
            inflater,
            R.layout.fragment_pdf_viewer,
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
        binding.pdfviewerToolbar.setNavigationIcon(R.drawable.ic_icon_go_to_back)
        binding.pdfviewerToolbar.setNavigationOnClickListener(View.OnClickListener {
            requireActivity().onBackPressed()
        })

        binding.pdfViewer.webViewClient = WebViewClient()
        binding.pdfViewer.settings.displayZoomControls = false
        binding.pdfViewer.settings.builtInZoomControls = false
        binding.pdfViewer.clearCache(true)
        binding.pdfViewer.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
//                showProgressDialog()
            }
            override fun onPageFinished(view: WebView, url: String) {
                val url =
                    "javascript:(function() {" + "document.querySelector(\".ndfHFb-c4YZDc-Wrql6b\").remove();})()"
                pdf_viewer.loadUrl(url)
                hideProgressDialog()
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
        binding.pdfViewer.settings.javaScriptEnabled = true
        showToast(arguments?.getString("pdf_src").toString())
        binding.pdfViewer.loadUrl(arguments?.getString("pdf_src").toString())
    }

}