package com.softsquared.instagramlagame.src.main.home.comment.models


import com.google.gson.annotations.SerializedName
import com.softsquared.instagramlagame.config.BaseResponse

data class CommentPostResponse(
    @SerializedName("result")
    val result: String
) : BaseResponse()