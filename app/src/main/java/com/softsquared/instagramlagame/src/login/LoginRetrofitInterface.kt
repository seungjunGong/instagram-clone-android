package com.softsquared.instagramlagame.src.login

import com.softsquared.instagramlagame.src.login.models.LoginResponse
import com.softsquared.instagramlagame.src.login.models.PostLoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRetrofitInterface {

    @POST("/auth/logIn")
    fun postLogin(@Body params: PostLoginRequest): Call<LoginResponse>

}