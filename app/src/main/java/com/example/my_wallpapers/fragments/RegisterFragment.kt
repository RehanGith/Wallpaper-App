package com.example.my_wallpapers.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.my_wallpapers.R
import com.example.my_wallpapers.databinding.FragmentRegisterBinding
import com.example.my_wallpapers.util.RegisterValidation
import com.example.my_wallpapers.util.Response
import com.example.my_wallpapers.viewmodels.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment: Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                registerViewModel.register.collect {
                    when(it) {
                        is Response.Failure -> {
                            Log.d("RegisterFragment", it.message.toString())
                            binding.buttonRegisterRegister.revertAnimation()
                        }
                        is Response.Loading -> {
                            binding.buttonRegisterRegister.startAnimation()
                        }
                        is Response.Success -> {
                            Log.d("RegisterFragment", it.data.toString())
                            binding.buttonRegisterRegister.revertAnimation()
                        }
                        is Response.UnSpecifies -> Unit
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                registerViewModel.validation.collect { validation ->
                    if(validation.email is RegisterValidation.failure) {
                        with(binding.edEmailRegister) {
                            requestFocus()
                            error = validation.email.message
                        }
                    }
                    if(validation.password is RegisterValidation.failure) {
                        with(binding.edPasswordRegister) {
                            requestFocus()
                            error = validation.password.message
                        }
                    }
                }
            }
        }
    }
}