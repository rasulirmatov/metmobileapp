package com.example.metproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.metproject.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {
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
                Navigation.findNavController(it)
                    .navigate(R.id.action_signInFragment_to_mainFragment)
            }
        }

    }
}