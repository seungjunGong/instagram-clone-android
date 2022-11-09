package com.softsquared.instagramlagame.src.main.others.models


import com.google.gson.annotations.SerializedName
import com.softsquared.instagramlagame.config.BaseResponse

data class ProfileGetDataResponse(
    @SerializedName("result")
    val resultProfileGetData: ResultProfileGetData
) : BaseResponse()