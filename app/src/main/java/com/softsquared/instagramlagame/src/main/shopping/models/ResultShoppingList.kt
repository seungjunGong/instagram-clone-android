package com.softsquared.instagramlagame.src.main.shopping.models


import com.google.gson.annotations.SerializedName

data class ResultShoppingList(
    @SerializedName("postId")
    val postId: Int,
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String
)