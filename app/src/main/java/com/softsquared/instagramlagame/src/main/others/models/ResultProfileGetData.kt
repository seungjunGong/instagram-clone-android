package com.softsquared.instagramlagame.src.main.others.models


import com.google.gson.annotations.SerializedName

data class ResultProfileGetData(
    @SerializedName("description")
    val description: String,
    @SerializedName("followTogetherList")
    val followTogetherList: List<Any>,
    @SerializedName("followerCount")
    val followerCount: Int,
    @SerializedName("followingCount")
    val followingCount: Int,
    @SerializedName("followingStatus")
    val followingStatus: String,
    @SerializedName("followingTogetherCount")
    val followingTogetherCount: Int,
    @SerializedName("link")
    val link: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("postCount")
    val postCount: Int,
    @SerializedName("profileUrl")
    val profileUrl: String
)