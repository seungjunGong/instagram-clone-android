package com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models


import com.google.gson.annotations.SerializedName
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.FeedResult

data class ResultFeedPage(
    @SerializedName("numOfPosts")
    val numOfPosts: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("postList")
    val postList: List<FeedResult>
)