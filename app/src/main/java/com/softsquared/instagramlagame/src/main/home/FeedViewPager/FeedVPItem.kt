package com.softsquared.instagramlagame.src.main.home.FeedViewPager

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FeedVpItemBinding
import com.softsquared.instagramlagame.src.main.home.ui.FeedRVD

class FeedVPItem(val imgRes : String) :
    BaseFragment<FeedVpItemBinding>(FeedVpItemBinding::bind, R.layout.feed_vp_item) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this).load(imgRes).into(binding.feedIv)
    }
}