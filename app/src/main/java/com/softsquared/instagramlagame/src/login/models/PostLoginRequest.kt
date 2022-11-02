package com.softsquared.instagramlagame.src.login.models


import com.google.gson.annotations.SerializedName

data class PostLoginRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: String
)