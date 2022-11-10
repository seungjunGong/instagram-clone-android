package com.softsquared.instagramlagame.src.main.home.post.posting.models


import com.google.gson.annotations.SerializedName

data class UploadPostingResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: String
)