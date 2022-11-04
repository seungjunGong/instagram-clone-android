package com.softsquared.instagramlagame.src.main.profile.edit.models


import com.google.gson.annotations.SerializedName
import com.softsquared.instagramlagame.config.BaseResponse

data class ProFileEditResponse(
    @SerializedName("result")
    val result: String
) : BaseResponse()