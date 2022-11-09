package com.softsquared.instagramlagame.src.main.home.whole_recyclerview.story.models


import com.google.gson.annotations.SerializedName

data class ResultHomeStory(
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("profileUrl")
    val profileUrl: String,
    @SerializedName("storyDataList")
    val storyDataList: List<StoryData>,
    @SerializedName("visitCnt")
    val visitCnt: Int
)