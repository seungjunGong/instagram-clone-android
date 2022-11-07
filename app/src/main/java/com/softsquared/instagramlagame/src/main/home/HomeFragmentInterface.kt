package com.softsquared.instagramlagame.src.main.home

import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.HomeFeedResponse
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.FeedLikeResponse

interface HomeFragmentInterface {

    fun onGetFeedSuccess(response: HomeFeedResponse)

    fun onGetFeedFailure(message: String)

    fun onPostFeedLikeSuccess(response: FeedLikeResponse)

    fun onPostFeedLikeFailure(message: String)

    fun onPatchFeedLikeSuccess(response: FeedLikeResponse)

    fun onPatchFeedLikeFailure(message: String)
}