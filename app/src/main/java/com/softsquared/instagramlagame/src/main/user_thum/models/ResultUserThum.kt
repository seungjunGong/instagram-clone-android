package com.softsquared.instagramlagame.src.main.user_thum.models


import com.google.gson.annotations.SerializedName

data class ResultUserThum(
    @SerializedName("numOfPosts")
    val numOfPosts: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("thumbnailList")
    val thumbnailList: List<Thumbnail>,
    @SerializedName("userId")
    val userId: Int
)