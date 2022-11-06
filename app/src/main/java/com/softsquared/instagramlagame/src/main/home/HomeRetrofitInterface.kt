package com.softsquared.instagramlagame.src.main.home

import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.HomeFeedResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeRetrofitInterface {

    @GET("/posts/home/:page")
    fun getFeed(@Path("page") page: Int) : Call<HomeFeedResponse>

}