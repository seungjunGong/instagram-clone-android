package com.softsquared.instagramlagame.src.main.home.comment.models


import com.google.gson.annotations.SerializedName

data class ResultCommentDetail(
    @SerializedName("content")
    val content: String,
    @SerializedName("modifyCheck")
    val modifyCheck: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("postId")
    val postId: Int,
    @SerializedName("storyExist")
    val storyExist: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("userImg")
    val userImg: String
)