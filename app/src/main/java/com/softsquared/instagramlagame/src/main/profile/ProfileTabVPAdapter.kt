package com.softsquared.instagramlagame.src.main.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.softsquared.instagramlagame.src.main.profile.tab.ProFilePostFragment
import com.softsquared.instagramlagame.src.main.profile.tab.ProFileTagFragment

class ProfileTabVPAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val TYPE_POST = 0
    private val TYPE_TAG = 1

    private var listType: List<Int> = listOf( TYPE_POST, TYPE_TAG)

    override fun getItemCount(): Int {
        return listType.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            TYPE_POST -> ProFileTagFragment()
            else -> ProFileTagFragment()
        }
    }
}