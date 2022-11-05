package com.softsquared.instagramlagame.src.main.profile.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class ResultProFileMyData(
    @SerializedName("description")
    val description: String = "",
    @SerializedName("followerCount")
    val followerCount: Int = 0,
    @SerializedName("followingCount")
    val followingCount: Int = 0,
    @SerializedName("link")
    val link: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("nickname")
    val nickname: String = "",
    @SerializedName("postCount")
    val postCount: Int = 0,
    @SerializedName("profileUrl")
    val profileUrl: String = "",
    @SerializedName("userId")
    val userId: Int = 0
)