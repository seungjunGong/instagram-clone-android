package com.softsquared.instagramlagame.src.main.home.post

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder

class PostTabVPAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private var listType: List<Int> = listOf(0, 1)

    override fun getItemCount(): Int {
        return listType.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                PostPostingFragment()
            }
            else -> PostStoryFragment()
        }
    }

}