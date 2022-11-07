package com.softsquared.instagramlagame.src.main.profile

import android.os.Bundle
import android.view.View
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentMySettingBinding


class ProfileSettingFragment : BaseFragment<FragmentMySettingBinding>(FragmentMySettingBinding::bind,
    R.layout.fragment_my_setting) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mySettingLogoutBt.setOnClickListener {

        }
    }
}