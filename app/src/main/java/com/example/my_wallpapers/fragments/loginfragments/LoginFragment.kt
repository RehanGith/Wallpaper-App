package com.example.my_wallpapers.fragments.loginfragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.my_wallpapers.R
import com.example.my_wallpapers.app.WallpaperActivity
import com.example.my_wallpapers.databinding.FragmentLoginBinding
import com.example.my_wallpapers.dialog.setUpBottomDialog
import com.example.my_wallpapers.util.Response
import com.example.my_wallpapers.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
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
        binding.tvDontHaveAccount.setOnClickListener {
            val navOption = NavOptions.Builder()
                .setPopUpTo(R.id.loginFragment, true)
                .build()
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment, null, navOption)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.login.collect {
                    when(it) {
                        is Response.Failure -> {
                            Log.d("LoginFragment", "onViewCreated: ${it.message}")
                            binding.buttonLoginLogin.revertAnimation()
                            Toast.makeText(requireContext(), "Error: ${ it.message }", Toast.LENGTH_LONG).show()

                        }
                        is Response.Loading -> {
                            binding.buttonLoginLogin.startAnimation()
                        }
                        is Response.Success -> {
                            Log.d("LoginFragment", "onViewCreated Data: ${it.data}")
                            binding.buttonLoginLogin.revertAnimation()
                            val intent = Intent(requireContext(), WallpaperActivity::class.java).apply {
                                // These flags clear the existing task and start a new one
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            }
                            startActivity(intent)

                            requireActivity().finish()
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