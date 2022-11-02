package com.softsquared.instagramlagame.src.signup.phone_email.models


import com.google.gson.annotations.SerializedName
import com.softsquared.instagramlagame.config.BaseResponse

data class PhoneEmailResponse(
    @SerializedName("result")
    val result: String
) : BaseResponse()