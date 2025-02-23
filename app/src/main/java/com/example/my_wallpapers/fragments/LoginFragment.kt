package com.example.my_wallpapers.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.my_wallpapers.R
import com.example.my_wallpapers.databinding.FragmentLoginBinding
import com.example.my_wallpapers.dialog.setUpBottomDialog
import com.example.my_wallpapers.util.Response
import com.example.my_wallpapers.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        binding.tvForgotPasswordLogin.setOnClickListener {
            setUpBottomDialog {
                loginViewModel.resetPassword(it)
            }
        }
        binding.buttonLoginLogin.setOnClickListener {
            val email = binding.edEmailLogin.text.toString().trim()
            val password = binding.edPasswordLogin.text.toString()

            loginViewModel.login(email, password)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.login.collect {
                    when(it) {
                        is Response.Failure -> {
                            Log.d("LoginFragment", "onViewCreated: ${it.message}")
                            binding.buttonLoginLogin.revertAnimation()
                        }
                        is Response.Loading -> {
                            binding.buttonLoginLogin.startAnimation()
                        }
                        is Response.Success -> {
                            Log.d("LoginFragment", "onViewCreated Data: ${it.data}")
                            binding.buttonLoginLogin.revertAnimation()
                        }
                        is Response.UnSpecifies -> Unit
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.resetPass.collect {
                    when(it) {
                        is Response.Failure -> {
                            Toast.makeText(requireContext(), "Something is wrong with your Email", Toast.LENGTH_LONG).show()
                        }
                        is Response.Loading -> {
                            Log.d("LoginFragment", "onViewCreated: Loading")
                        }
                        is Response.Success -> {
                            Toast.makeText(requireContext(), "Email is ben Sent to you email", Toast.LENGTH_LONG).show()
                        }
                        is Response.UnSpecifies -> Unit
                    }
                }
            }
        }

    }
}