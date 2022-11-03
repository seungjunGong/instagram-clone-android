package com.softsquared.instagramlagame.src.main.profile

import com.softsquared.instagramlagame.src.main.profile.models.ProFileMyDataResponse
import retrofit2.Call
import retrofit2.http.GET


interface ProFileRetrofitInterface {

    @GET("/profile/getMyData")
    fun getProfileMyData() : Call<ProFileMyDataResponse>
}