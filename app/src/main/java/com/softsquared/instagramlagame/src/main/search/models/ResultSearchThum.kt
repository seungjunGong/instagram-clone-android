package com.softsquared.instagramlagame.src.main.search.models


import com.google.gson.annotations.SerializedName

data class ResultSearchThum(
    @SerializedName("imgUrl")
    val imgUrl: String,
    @SerializedName("postId")
    val postId: Int
)