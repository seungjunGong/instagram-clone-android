package com.softsquared.instagramlagame.src.main.profile.edit.models


import com.google.gson.annotations.SerializedName

data class PatchProFileEditRequest(
    @SerializedName("description")
    val description: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("profileUrl")
    val profileUrl: String
)