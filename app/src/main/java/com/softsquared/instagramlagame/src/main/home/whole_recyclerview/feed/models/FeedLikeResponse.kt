package com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models


import com.google.gson.annotations.SerializedName
import com.softsquared.instagramlagame.config.BaseResponse

data class FeedLikeResponse(
    @SerializedName("result")
    val result: String
) : BaseResponse()