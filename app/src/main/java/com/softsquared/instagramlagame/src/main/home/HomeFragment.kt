package com.softsquared.instagramlagame.src.main.home

import android.os.Bundle
import android.view.View
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentHomeBinding
import com.softsquared.instagramlagame.src.main.home.ui.FeedData
import com.softsquared.instagramlagame.src.main.home.ui.FeedRVD
import com.softsquared.instagramlagame.src.main.home.ui.StoryData

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home){



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var feedData = ArrayList<FeedData>()
        feedData.apply {
            add(FeedData(1))
            add(FeedData(1))
            add(FeedData(1))
            add(FeedData(1))
            add(FeedData(1))
            add(FeedData(1))
            add(FeedData(1))
            add(FeedData(1))
            add(FeedData(1))
            add(FeedData(1))
        }

        var storyData = ArrayList<StoryData>()
        storyData.apply {
            add(StoryData(1))
            add(StoryData(1))
            add(StoryData(1))
            add(StoryData(1))
            add(StoryData(1))
            add(StoryData(1))
            add(StoryData(1))
        }


        val homeRVD = FeedRVD(feedData, storyData)
        binding.homeRcv.adapter = homeRVD

    }
}