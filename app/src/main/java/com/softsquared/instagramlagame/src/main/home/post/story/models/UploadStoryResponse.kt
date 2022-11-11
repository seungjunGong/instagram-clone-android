package com.softsquared.instagramlagame.src.main.home.post.story.models


import com.google.gson.annotations.SerializedName
import com.softsquared.instagramlagame.config.BaseResponse

data class UploadStoryResponse(

    @SerializedName("result")
    val result: ResultUploadStory
) : BaseResponse()