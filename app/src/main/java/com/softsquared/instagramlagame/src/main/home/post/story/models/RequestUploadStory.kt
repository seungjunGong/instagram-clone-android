package com.softsquared.instagramlagame.src.main.home.post.story.models


import com.google.gson.annotations.SerializedName

data class RequestUploadStory(
    @SerializedName("storyImgUrl")
    val storyImgUrl: String,
    @SerializedName("userTagList")
    val userTagList: List<String>?
)