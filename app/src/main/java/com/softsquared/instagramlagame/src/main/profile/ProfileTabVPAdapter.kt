package com.softsquared.instagramlagame.src.main.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.softsquared.instagramlagame.src.main.profile.tab.ProFileTabFragment

class ProfileTabVPAdapter(fragmentActivity: FragmentActivity, private val inReels : Boolean) : FragmentStateAdapter(fragmentActivity) {

    private var listINREELSType: List<Int> = listOf(0, 1, 2)
    private var listBASICEType: List<Int> = listOf(0, 1)

    override fun getItemCount(): Int {
        return if(inReels){
            listINREELSType.size
        }else{
            listBASICEType.size
        }
    }

    override fun createFragment(position: Int): Fragment {
        if(inReels){
            return when(position){
                0 -> ProFileTabFragment("post")
                1 -> ProFileTabFragment("reels")
                else -> ProFileTabFragment("tag")
            }
        } else {
            return when(position){
                0 -> ProFileTabFragment("post")
                else -> ProFileTabFragment("tag")
            }
        }
    }
}