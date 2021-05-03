package com.example.metproject

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_termsof_agreement_dialog.*


class TermsofAgreementFragment : BottomSheetDialogFragment() {

    lateinit var preferences: SharedPreferences
    val pref_show_intro = "Intro"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_termsof_agreement_dialog, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME,R.style.CustomBottomSheetDialogTheme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = this.requireActivity().getSharedPreferences("IntroSlider", Context.MODE_PRIVATE)

        bottom_sheet_confirm_button.setOnClickListener(){
            if (bottom_sheet_accept_checkbox.isChecked) {
                val editor = preferences.edit()
                editor.putBoolean(pref_show_intro, false)
                editor.apply()
                if (!preferences.getBoolean(pref_show_intro, true)) {
                    view.let {
                        requireParentFragment().findNavController().navigate(R.id.action_termsofAgreementFragment_to_home)
                    }
                }
            }else{
                Toast.makeText(context,"Шартномаро ҳатман кабул Кунед",Toast.LENGTH_SHORT).show()
            }


        }

    }
}