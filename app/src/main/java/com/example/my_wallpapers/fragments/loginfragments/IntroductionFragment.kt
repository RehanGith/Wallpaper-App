package com.example.my_wallpapers.fragments.loginfragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController

import com.example.my_wallpapers.R
import com.example.my_wallpapers.app.WallpaperActivity
import com.example.my_wallpapers.databinding.FragmentIntroductionBinding
import com.example.my_wallpapers.viewmodels.IntroductionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class IntroductionFragment : Fragment(R.layout.fragment_introduction) {
    private lateinit var binding: FragmentIntroductionBinding
    private val introductionViewModel by viewModels<IntroductionViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentIntroductionBinding.bind(view)

        binding.btnStart.setOnClickListener {
            introductionViewModel.startButtonClick()
            findNavController().navigate(R.id.action_introductionFragment_to_accountOptionFragment)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                introductionViewModel.navigation.collect {
                    when (it) {
                        IntroductionViewModel.SHOPPING_FRAGMENT -> {
                            val intent =
                                Intent(requireContext(), WallpaperActivity::class.java).apply {
                                    // These flags clear the existing task and start a new one
                                    flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                }
                            startActivity(intent)
                            requireActivity().finish()
                        }
                        IntroductionViewModel.ACTION_OFTEN_FRAGMENT -> {
                            findNavController().navigate(it)
                        }
                        else -> Unit
                    }
                }

            }
        }
    }
}