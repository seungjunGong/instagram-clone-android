package com.softsquared.instagramlagame.src.signup.user_name.models


import com.google.gson.annotations.SerializedName
import com.softsquared.instagramlagame.config.BaseResponse

data class UserNameResponse(
    @SerializedName("result")
    val result: String
) : BaseResponse()