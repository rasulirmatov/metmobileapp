package com.app.maktabielektroni

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.app.maktabielektroni.databinding.FragmentSignInBinding
import com.app.maktabielektroni.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val dataBinding = DataBindingUtil.inflate<FragmentSignUpBinding>(
            inflater,
            R.layout.fragment_sign_up,
            container,
            false
        )
        dataBinding.lifecycleOwner = this
        binding = dataBinding
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener(){
//            Navigation.findNavController(it)
//                .navigate(R.id.action_signUpFragment_to_signInFragment)
            requireActivity().onBackPressed()
        }

    }

}