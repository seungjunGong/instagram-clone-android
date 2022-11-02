package com.softsquared.instagramlagame.src.signup

import com.softsquared.instagramlagame.src.signup.phone_email.models.PhoneEmailResponse
import com.softsquared.instagramlagame.src.signup.sginup_models.PostSignUpRequest
import com.softsquared.instagramlagame.src.signup.sginup_models.SignUpResponse
import com.softsquared.instagramlagame.src.signup.user_name.models.UserNameResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SignUpRetrofitInterface {

    @GET("/users/check/id")
    fun getPhoneEmailCheck(@Query("userKey") phoneEmail: String) : Call<PhoneEmailResponse>

    @GET("/users/check/nick")
    fun getUserNameCheck(@Query("nickName") userName: String) : Call<UserNameResponse>

    @POST("/users/signUp")
    fun postSignUp(@Body params: PostSignUpRequest): Call<SignUpResponse>

}