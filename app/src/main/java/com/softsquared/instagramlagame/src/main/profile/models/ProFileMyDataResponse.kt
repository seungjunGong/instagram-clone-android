package com.softsquared.instagramlagame.src.main.profile.models


import com.google.gson.annotations.SerializedName
import com.softsquared.instagramlagame.config.BaseResponse

data class ProFileMyDataResponse(
    @SerializedName("result")
    val resultProFileMyData: ResultProFileMyData
) : BaseResponse()