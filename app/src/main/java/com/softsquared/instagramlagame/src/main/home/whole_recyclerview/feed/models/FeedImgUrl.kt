package com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models


import com.google.gson.annotations.SerializedName

data class FeedImgUrl(
    @SerializedName("imgId")
    val imgId: Int,
    @SerializedName("imgUrl")
    val imgUrl: String
)