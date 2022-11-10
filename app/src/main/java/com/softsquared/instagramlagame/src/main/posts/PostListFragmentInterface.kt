package com.softsquared.instagramlagame.src.main.posts

import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.HomeFeedResponse

interface PostListFragmentInterface {

    fun onGetPostListSuccess(response: HomeFeedResponse)

    fun onGetPostListFailure(message: String)
}