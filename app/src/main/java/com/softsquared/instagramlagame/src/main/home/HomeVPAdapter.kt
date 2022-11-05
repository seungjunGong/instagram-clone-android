package com.softsquared.instagramlagame.src.main.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.softsquared.instagramlagame.src.main.home.post.PostFragment

class HomeVPAdapter(fragmentActivity: FragmentActivity) :FragmentStateAdapter(fragmentActivity) {

    private var listType: List<Int> = listOf(0, 1)

    override fun getItemCount(): Int {
        return listType.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> PostFragment()
            else -> HomeFragment()
        }
    }
}