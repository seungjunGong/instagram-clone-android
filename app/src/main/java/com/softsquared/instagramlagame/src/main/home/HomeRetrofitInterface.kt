package com.softsquared.instagramlagame.src.main.home

import com.softsquared.instagramlagame.src.main.home.comment.models.CommentDetailResponse
import com.softsquared.instagramlagame.src.main.home.comment.models.CommentPageResponse
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.HomeFeedResponse
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.FeedLikeResponse
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.story.models.HomeStoryResponse
import retrofit2.Call
import retrofit2.http.*

interface HomeRetrofitInterface {

    @GET("/posts/home/{page}")
    fun getFeed(@Path("page") page: Int) : Call<HomeFeedResponse>

    @POST("/posts/like/{postId}")
    fun postFeedLike(@Path("postId") postId: Int) : Call<FeedLikeResponse>

    @PATCH("/posts/like/{postId}")
    fun patchFeedLike(@Path("postId") postId: Int) : Call<FeedLikeResponse>

    @GET("/storys/getList")
    fun getHomeStory() : Call<HomeStoryResponse>

    @GET("/posts/detail/{postId}")
    fun getCommentDetail(@Path("postId") postId: Int) : Call<CommentDetailResponse>

    @GET("/posts/comments")
    fun patchCommentPage(@Query("postId") postId: Int, @Query("page") page : Int) : Call<CommentPageResponse>

    @GET("/posts/user/list")
    fun getPostList(@Query("postId") postId: Int, @Query("page") page: Int): Call<HomeFeedResponse>
}