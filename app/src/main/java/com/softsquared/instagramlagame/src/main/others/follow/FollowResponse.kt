package com.softsquared.instagramlagame.src.main.others.follow


import com.google.gson.annotations.SerializedName
import com.softsquared.instagramlagame.config.BaseResponse

data class FollowResponse(
    @SerializedName("result")
    val result: String
) : BaseResponse()