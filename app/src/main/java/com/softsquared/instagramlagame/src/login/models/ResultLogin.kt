package com.softsquared.instagramlagame.src.login.models


import com.google.gson.annotations.SerializedName

data class ResultLogin(
    @SerializedName("firstLogin")
    val firstLogin: Boolean,
    @SerializedName("jwt")
    val jwt: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("userIdx")
    val userIdx: Int
)