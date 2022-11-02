package com.softsquared.instagramlagame.src.signup.sginup_models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostSignUpRequest (
    @SerializedName("birth")
    val birth: String = "",
    @SerializedName("name")
    val id: String = "",
    @SerializedName("nickname")
    val user_name: String = "",
    @SerializedName("password")
    val password: String = "",
    @SerializedName("userKey")
    val phoneEmail: String = ""
):Parcelable