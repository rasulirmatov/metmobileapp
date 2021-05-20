package com.app.maktabielektroni

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.app.maktabielektroni.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {
    lateinit var preferences: SharedPreferences
    val pref_show_sign_in = "Sign_In"


    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentSignInBinding>(
            inflater,
            R.layout.fragment_sign_in,
            container,
            false
        )
        dataBinding.lifecycleOwner = this
        binding = dataBinding
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        requireActivity().window.decorView.systemUiVisibility = 0
//        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(),R.color.primary);// set status background white

        preferences = this.requireActivity().getSharedPreferences("SignIn", Context.MODE_PRIVATE)

        binding.goToSignup.setOnClickListener { view ->
            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_signInFragment_to_signUpFragment)
            }
        }

        binding.forgotPassword.setOnClickListener {
            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_signInFragment_to_forgotPasswordFragment)
            }
        }

        binding.skipSignIn.setOnClickListener{
            view?.let {
                val editor = preferences.edit()
                editor.putBoolean(pref_show_sign_in, false)
                editor.apply()
                Navigation.findNavController(it)
                    .navigate(R.id.action_signInFragment_to_mainFragment)
            }
        }




    }
}