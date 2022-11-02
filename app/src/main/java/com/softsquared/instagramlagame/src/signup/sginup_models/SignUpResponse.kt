package com.softsquared.instagramlagame.src.signup.sginup_models


import com.google.gson.annotations.SerializedName
import com.softsquared.instagramlagame.config.BaseResponse

data class SignUpResponse(
    @SerializedName("result")
    val resultSignUp: ResultSignUp
) : BaseResponse()