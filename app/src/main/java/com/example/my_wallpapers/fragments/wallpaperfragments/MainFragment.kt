package com.example.my_wallpapers.fragments.wallpaperfragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.my_wallpapers.R
import com.example.my_wallpapers.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.combine


class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding

    val tabTitles = listOf("Home", "Popular","Random", "Categories")
    val fragments = listOf(HomeFragment(), PopularFragment(), RandomFragment(), CategoryFragment())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        initTabLayout()
    }

    private fun initTabLayout() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) {tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

}