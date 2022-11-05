package com.softsquared.instagramlagame.src.main.home

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentHomeVpControllerBinding


class HomeVpControllerFragment : BaseFragment<FragmentHomeVpControllerBinding>(FragmentHomeVpControllerBinding::bind, R.layout.fragment_home_vp_controller) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.homeVpController.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val homeVPAdapter = HomeVPAdapter(requireActivity())
        binding.homeVpController.adapter = homeVPAdapter

        binding.homeVpController.currentItem = 1





    }
}