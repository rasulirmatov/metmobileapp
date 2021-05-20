package com.app.maktabielektroni

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.app.maktabielektroni.utils.CustomProgressDialog
import org.jsoup.Jsoup





open class BaseFragment : Fragment() {

    private var mProgressDialog: CustomProgressDialog? = null

    fun showToast(message: String) {
        Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun shareIntent(url: String) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, url);
        startActivity(Intent.createChooser(shareIntent, "Lesson"))
    }

    open fun showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = CustomProgressDialog(requireContext())
        }
        if (!mProgressDialog!!.isShowing && !requireActivity().isFinishing && isAdded) {
            mProgressDialog!!.setCancelable(true)
            mProgressDialog!!.setCanceledOnTouchOutside(false)
            mProgressDialog!!.show("Загрузка...")
        }
    }

    open fun hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.dismiss()
        }
    }

//    open fun isNetworkAvailable(): Boolean {
//        val connMgr = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val networkInfo = connMgr.activeNetworkInfo
//        return networkInfo!!.isConnected
//    }

    open fun isNetworkAvailable(): Boolean {
        val connectivityManager = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    open fun removeHtmlTags(html: String?): String? {
        return Jsoup.parse(html).text()
    }

    open fun bundleForWebViewer(title: String, url: String) : Bundle {
        val bundle = bundleOf("url" to url)
        bundle.putString("title", title)
        return bundle
    }




}