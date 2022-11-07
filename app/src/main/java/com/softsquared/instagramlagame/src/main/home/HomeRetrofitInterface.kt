package com.softsquared.instagramlagame.src.main.home

import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.HomeFeedResponse
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.FeedLikeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface HomeRetrofitInterface {

    @GET("/posts/home/{page}")
    fun getFeed(@Path("page") page: Int) : Call<HomeFeedResponse>

    @POST("/posts/like/{postId}")
    fun postFeedLike(@Path("postId") postId: Int) : Call<FeedLikeResponse>

    @PATCH("/posts/like/{postId}")
    fun patchFeedLike(@Path("postId") postId: Int) : Call<FeedLikeResponse>

}