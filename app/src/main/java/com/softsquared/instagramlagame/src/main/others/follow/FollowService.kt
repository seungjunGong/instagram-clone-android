package com.softsquared.instagramlagame.src.main.others.follow

import com.softsquared.instagramlagame.config.ApplicationClass
import com.softsquared.instagramlagame.src.main.others.OthersProfileRetrofitInterface
import retrofit2.Call
import retrofit2.Response

class FollowService (val followInterface: FollowInterface) {

    fun tryPostFollow(targetId: Int){
        val followRetrofitInterface = ApplicationClass.sRetrofit.create(OthersProfileRetrofitInterface::class.java)
        followRetrofitInterface.postFollowOthers(targetId).enqueue(object : retrofit2.Callback<FollowResponse>{
            override fun onResponse(
                call: Call<FollowResponse>,
                response: Response<FollowResponse>,
            ) {
                followInterface.onPostFollowSuccess(response.body() as FollowResponse)
            }

            override fun onFailure(call: Call<FollowResponse>, t: Throwable) {
                followInterface.onPostFollowFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryPatchUnFollow(targetId: Int){
        val followRetrofitInterface = ApplicationClass.sRetrofit.create(OthersProfileRetrofitInterface::class.java)
        followRetrofitInterface.patchUnFollowOthers(targetId).enqueue(object : retrofit2.Callback<FollowResponse>{
            override fun onResponse(
                call: Call<FollowResponse>,
                response: Response<FollowResponse>,
            ) {
                followInterface.onPatchUnFollowSuccess(response.body() as FollowResponse)
            }

            override fun onFailure(call: Call<FollowResponse>, t: Throwable) {
                followInterface.onPatchUnFollowFailure(t.message ?: "통신 오류")
            }

        })
    }
}