package com.softsquared.instagramlagame.src.main.home.whole_recyclerview.story.models


import com.google.gson.annotations.SerializedName

data class StoryData(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("imgUrl")
    val imgUrl: String,
    @SerializedName("storyId")
    val storyId: Int,
    @SerializedName("userTagList")
    val userTagList: List<Any>
)