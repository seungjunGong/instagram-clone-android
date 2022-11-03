package com.softsquared.instagramlagame.src.main.profile.models


import com.google.gson.annotations.SerializedName

data class ResultProFileMyData(
    @SerializedName("description")
    val description: String,
    @SerializedName("followerCount")
    val followerCount: Int,
    @SerializedName("followingCount")
    val followingCount: Int,
    @SerializedName("link")
    val link: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("postCount")
    val postCount: Int,
    @SerializedName("profileUrl")
    val profileUrl: String,
    @SerializedName("userId")
    val userId: Int
)