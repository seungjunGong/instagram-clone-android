package com.softsquared.instagramlagame.src.main.home.post.posting.models


import com.google.gson.annotations.SerializedName

data class RequestUploadPostingData(
    @SerializedName("content")
    val content: String,
    @SerializedName("hashTagList")
    val hashTagList: List<String>,
    @SerializedName("imgUrlList")
    val imgUrlList: List<String>,
    @SerializedName("reels")
    val reels: String,
    @SerializedName("reelsMusic")
    val reelsMusic: Any?,
    @SerializedName("userTagList")
    val userTagList: List<Int>
)