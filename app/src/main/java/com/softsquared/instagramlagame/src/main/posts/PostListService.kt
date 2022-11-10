package com.softsquared.instagramlagame.src.main.posts

import com.softsquared.instagramlagame.config.ApplicationClass
import com.softsquared.instagramlagame.src.main.home.HomeRetrofitInterface
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.HomeFeedResponse
import retrofit2.Call
import retrofit2.Response

class PostListService (val postListFragmentInterface: PostListFragmentInterface) {

    fun tryGetPostList(getPostId: Int, getPage: Int){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getPostList(getPostId, getPage).enqueue(object : retrofit2.Callback<HomeFeedResponse>{
            override fun onResponse(
                call: Call<HomeFeedResponse>,
                response: Response<HomeFeedResponse>,
            ) {
                postListFragmentInterface.onGetPostListSuccess(response.body() as HomeFeedResponse)
            }

            override fun onFailure(call: Call<HomeFeedResponse>, t: Throwable) {
                postListFragmentInterface.onGetPostListFailure(t.message ?: "통신 오류")
            }

        })
    }
}