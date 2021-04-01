package com.example.metproject

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.metproject.utils.CustomProgressDialog

open class BaseFragment : Fragment() {

    private var mProgressDialog: CustomProgressDialog? = null

    fun showToast (message:String){
        Toast.makeText(this.requireContext(),message,Toast.LENGTH_SHORT).show()
    }

    fun shareIntent (url:String){
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
        if (!mProgressDialog!!.isShowing && !requireActivity().isFinishing) {
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


}