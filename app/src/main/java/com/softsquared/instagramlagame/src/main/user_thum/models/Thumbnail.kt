package com.softsquared.instagramlagame.src.main.user_thum.models


import com.google.gson.annotations.SerializedName

data class Thumbnail(
    @SerializedName("postId")
    val postId: Int,
    @SerializedName("thumbnail")
    val thumbnail: String
)