package com.softsquared.instagramlagame.src.main.user_thum.models


import com.google.gson.annotations.SerializedName

data class GetUserThumRequest(
    @SerializedName("myProfile")
    val myProfile: String,
    @SerializedName("page")
    val page: Int,
    @SerializedName("userId")
    val userId: Int
)