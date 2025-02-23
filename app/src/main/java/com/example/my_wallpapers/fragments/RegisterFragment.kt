package com.example.my_wallpapers.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.my_wallpapers.R
import com.example.my_wallpapers.databinding.FragmentRegisterBinding
import com.example.my_wallpapers.viewmodels.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment: Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
    }
}