package com.softsquared.instagramlagame.src.main.others

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.softsquared.instagramlagame.src.main.profile.tab.ProFileTabFragment

class OthersTabVPAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private var listBASICEType: List<Int> = listOf(0, 1)

    override fun getItemCount(): Int =  listBASICEType.size

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> OthersTabFragment("post")
            else -> OthersTabFragment("tag")
        }
    }


}