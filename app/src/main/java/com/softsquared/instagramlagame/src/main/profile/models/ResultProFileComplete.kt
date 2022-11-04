package com.softsquared.instagramlagame.src.main.profile.models


import com.google.gson.annotations.SerializedName

data class ResultProFileComplete(
    @SerializedName("description")
    val description: Boolean,
    @SerializedName("follow")
    val follow: Boolean,
    @SerializedName("name")
    val name: Boolean,
    @SerializedName("profileImg")
    val profileImg: Boolean
)