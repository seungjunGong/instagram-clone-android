package com.softsquared.instagramlagame.src.login.models


import com.google.gson.annotations.SerializedName
import com.softsquared.instagramlagame.config.BaseResponse

data class LoginResponse(
    @SerializedName("result")
    val resultLogin: ResultLogin
) : BaseResponse()