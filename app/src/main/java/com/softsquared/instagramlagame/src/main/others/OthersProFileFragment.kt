package com.softsquared.instagramlagame.src.main.others

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentOthersProfileBinding
import com.softsquared.instagramlagame.src.main.profile.ProfileTabVPAdapter

class OthersProFileFragment : BaseFragment<FragmentOthersProfileBinding>(FragmentOthersProfileBinding::bind, R.layout.fragment_others_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뷰 페이저 탭 레이아웃 연결
        val othersTabAdapter = OthersTabVPAdapter(requireActivity())
        binding.profileTabVp.adapter = othersTabAdapter
        binding.profileTabVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.profileTagTabs, binding.profileTabVp) { tab, position ->
            tab.customView = getTabView(position)
        }.attach()
    }

    // 탭 레이아웃 커스텀
    private fun getTabView(position: Int): View {
        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.profile_tab_item, null, false)
        val ivTab = view.findViewById<ImageView>(R.id.ivTab)

        when (position) {
            0 -> ivTab.setImageResource(R.drawable.ic_profile_tab_post)
            else -> ivTab.setImageResource(R.drawable.ic_profile_tab_tag)
        }
        return view
    }
}