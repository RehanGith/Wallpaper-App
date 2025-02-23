package com.example.my_wallpapers.fragments.loginfragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.my_wallpapers.R
import com.example.my_wallpapers.databinding.FragmentAccountOptionBinding

class AccountOptionFragment : Fragment(R.layout.fragment_account_option) {
    private lateinit var binding: FragmentAccountOptionBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAccountOptionBinding.bind(view)

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_accountOptionFragment_to_loginFragment)
        }
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_accountOptionFragment_to_registerFragment)
        }
    }
}