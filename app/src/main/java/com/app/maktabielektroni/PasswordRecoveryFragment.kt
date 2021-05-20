package com.app.maktabielektroni

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.app.maktabielektroni.databinding.FragmentForgotPasswordBinding
import com.app.maktabielektroni.databinding.FragmentPasswordRecoveryBinding

class PasswordRecoveryFragment : Fragment() {
    private lateinit var binding: FragmentPasswordRecoveryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val dataBinding = DataBindingUtil.inflate<FragmentPasswordRecoveryBinding>(
            inflater,
            R.layout.fragment_password_recovery,
            container,
            false
        )
        dataBinding.lifecycleOwner = this
        binding = dataBinding
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.numberPasswordAcceptBtn.setOnClickListener { view ->
            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_passwordRecoveryFragment_to_changePasswordFragment)
            }
        }
        binding.backBtn.setOnClickListener {
            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_passwordRecoveryFragment_to_forgotPasswordFragment)
            }
        }
    }

}