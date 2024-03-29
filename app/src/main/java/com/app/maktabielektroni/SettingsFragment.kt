package com.app.maktabielektroni

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.app.maktabielektroni.databinding.FragmentSettingsBinding
import com.google.android.material.transition.MaterialFadeThrough
import com.google.firebase.messaging.FirebaseMessaging


class SettingsFragment : BaseFragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentSettingsBinding>(
            inflater,
            R.layout.fragment_settings,
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

        binding.settingsToolbar.setNavigationIcon(R.drawable.ic_icon_go_to_back)
        binding.settingsToolbar.setNavigationOnClickListener(View.OnClickListener {
            requireActivity().onBackPressed()
        })

        val appSettingPrefs: SharedPreferences =
            this.requireContext().getSharedPreferences("AppSettingPrefs", Context.MODE_PRIVATE)
        val sharedPrefsEdit: SharedPreferences.Editor = appSettingPrefs.edit()
        val isNightModeOn: Boolean = appSettingPrefs.getBoolean("NightMode", false)

        binding.themeToggle.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    sharedPrefsEdit.putBoolean("NightMode", true)
                    sharedPrefsEdit.apply()
                    requireActivity().recreate();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    sharedPrefsEdit.putBoolean("NightMode", false)
                    sharedPrefsEdit.apply()
                    requireActivity().recreate();
                }
            }
        })
        binding.themeToggle.isChecked = isNightModeOn

        binding.notificationToggle.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed) {
                if (isChecked) {
                    sharedPrefsEdit.putBoolean("ReceiveNotification", true)
                    sharedPrefsEdit.apply()
                    FirebaseMessaging.getInstance().subscribeToTopic("Notification")
                } else {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("Notification")
                    sharedPrefsEdit.putBoolean("ReceiveNotification", false)
                    sharedPrefsEdit.apply()
                }
            }
        })
        binding.notificationToggle.isChecked = appSettingPrefs.getBoolean("ReceiveNotification", true)


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
//        exitTransition = inflater.inflateTransition(R.transition.slide_right)
//        enterTransition = inflater.inflateTransition(R.transition.slide_left)

        enterTransition = MaterialFadeThrough()


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


}



