package com.example.my_wallpapers.fragments.wallpaperfragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.my_wallpapers.R
import com.example.my_wallpapers.adapter.ViewPagerAdapter
import com.example.my_wallpapers.app.WallpaperActivity
import com.example.my_wallpapers.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.combine


class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding

    val tabTitles = listOf("Home", "Popular","Random", "Category")
    val fragments = listOf(HomeFragment(), PopularFragment(), RandomFragment(), CategoryFragment())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        initViewPager()
        initTabLayout()
    }

    private fun initTabLayout() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) {tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
    private fun initViewPager() {
        val viewPager = ViewPagerAdapter(activity as WallpaperActivity, fragments)
        binding.viewPager.adapter = viewPager
        binding.viewPager.isUserInputEnabled = false
    }
    private fun initToolBar() {
        binding.customeToolbar.title = "Wallpaper"
        (activity as AppCompatActivity).setSupportActionBar(binding.customeToolbar)
    }

}