package com.softsquared.instagramlagame.src.main.home.whole_recyclerview.story.models


import com.google.gson.annotations.SerializedName
import com.softsquared.instagramlagame.config.BaseResponse

data class HomeStoryResponse(
    @SerializedName("result")
    val resultHomeStory: List<ResultHomeStory>
) : BaseResponse()