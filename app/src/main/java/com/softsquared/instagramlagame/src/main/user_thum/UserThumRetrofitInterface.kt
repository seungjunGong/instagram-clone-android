package com.softsquared.instagramlagame.src.main.user_thum


import com.softsquared.instagramlagame.src.main.user_thum.models.UserThumResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserThumRetrofitInterface {

    @GET("/posts/user/thumbnail")
    fun getUserThumbnail(@Query("userId") userId:Int, @Query("page") page:Int) : Call<UserThumResponse>
}