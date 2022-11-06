package com.softsquared.instagramlagame.src.main.home

import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.HomeFeedResponse

interface HomeFragmentInterface {

    fun onGetFeedSuccess(response: HomeFeedResponse)

    fun onGetFeedFailure(message: String)
}