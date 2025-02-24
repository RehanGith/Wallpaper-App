package com.example.my_wallpapers.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.my_wallpapers.app.WallpaperActivity
import javax.inject.Inject

class ViewPagerAdapter(container : WallpaperActivity , private val fragmentList : List<Fragment>) : FragmentStateAdapter(container) {
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}