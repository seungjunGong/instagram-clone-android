package com.softsquared.instagramlagame.src.main.home

import com.softsquared.instagramlagame.config.ApplicationClass.Companion.sRetrofit
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.FeedLikeResponse
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.HomeFeedResponse
import retrofit2.Call
import retrofit2.Response

class HomeService (val homeFragmentInterface: HomeFragmentInterface) {

    fun tryGetFeed(getPage : Int){
        val homeRetrofitInterface = sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getFeed(getPage).enqueue(object  : retrofit2.Callback<HomeFeedResponse>{
            override fun onResponse(
                call: Call<HomeFeedResponse>,
                response: Response<HomeFeedResponse>
            ) {
                homeFragmentInterface.onGetFeedSuccess(response.body() as HomeFeedResponse)
            }

            override fun onFailure(call: Call<HomeFeedResponse>, t: Throwable) {
                homeFragmentInterface.onGetFeedFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryPostFeedLike(getPostId : Int){
        val homeRetrofitInterface = sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.postFeedLike(getPostId).enqueue(object : retrofit2.Callback<FeedLikeResponse>{
            override fun onResponse(
                call: Call<FeedLikeResponse>,
                response: Response<FeedLikeResponse>
            ) {
                homeFragmentInterface.onPostFeedLikeSuccess(response.body() as FeedLikeResponse)
            }

            override fun onFailure(call: Call<FeedLikeResponse>, t: Throwable) {
                homeFragmentInterface.onPostFeedLikeFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPatchFeedLike(getPostId: Int){
        val homeRetrofitInterface = sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.patchFeedLike(getPostId).enqueue(object : retrofit2.Callback<FeedLikeResponse>{
            override fun onResponse(
                call: Call<FeedLikeResponse>,
                response: Response<FeedLikeResponse>
            ) {
                homeFragmentInterface.onPatchFeedLikeSuccess(response.body() as FeedLikeResponse)
            }

            override fun onFailure(call: Call<FeedLikeResponse>, t: Throwable) {
                homeFragmentInterface.onPatchFeedLikeFailure(t.message ?: "통신 오류")
            }
        })
    }
}