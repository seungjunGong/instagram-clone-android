package com.softsquared.instagramlagame.src.main.search.auto_search.models


import com.google.gson.annotations.SerializedName

data class ResultAutoSearch(
    @SerializedName("name")
    val name: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("profileUrl")
    val profileUrl: String,
    @SerializedName("storyStatus")
    val storyStatus: String,
    @SerializedName("userId")
    val userId: Int
)