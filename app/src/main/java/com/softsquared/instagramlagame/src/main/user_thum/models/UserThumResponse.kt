package com.softsquared.instagramlagame.src.main.user_thum.models


import com.google.gson.annotations.SerializedName
import com.softsquared.instagramlagame.config.BaseResponse

data class UserThumResponse(
    @SerializedName("result")
    val resultUserThum: ResultUserThum
) : BaseResponse()