package com.softsquared.instagramlagame.src.main.home.comment.models


import com.google.gson.annotations.SerializedName

data class RequestPostComment(
    @SerializedName("content")
    val content: String,
    @SerializedName("postId")
    val postId: Int
)