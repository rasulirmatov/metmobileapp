package com.app.maktabielektroni

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.app.maktabielektroni.databinding.FragmentForgotPasswordBinding
import com.app.maktabielektroni.databinding.FragmentSignInBinding

class ForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgotPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val dataBinding = DataBindingUtil.inflate<FragmentForgotPasswordBinding>(
            inflater,
            R.layout.fragment_forgot_password,
            container,
            false
        )
        dataBinding.lifecycleOwner = this
        binding = dataBinding
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sendRequestRecoveryButton.setOnClickListener { view ->
            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_forgotPasswordFragment_to_passwordRecoveryFragment)
            }
        }
        binding.backBtn.setOnClickListener {
            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_forgotPasswordFragment_to_signInFragment)
            }
        }
    }
}