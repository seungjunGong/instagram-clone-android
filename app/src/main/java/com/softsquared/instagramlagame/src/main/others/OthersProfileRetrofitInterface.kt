package com.softsquared.instagramlagame.src.main.others

import com.softsquared.instagramlagame.src.main.others.follow.FollowResponse
import com.softsquared.instagramlagame.src.main.others.models.ProfileGetDataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface OthersProfileRetrofitInterface {

    @GET("profile/getData")
    fun getProfileOthersData(@Query("targetNickname") nickname: String) : Call<ProfileGetDataResponse>

    @POST("/follows/follow")
    fun postFollowOthers(@Query("targetId") targetId: Int) : Call<FollowResponse>

    @PATCH("/follows/unFollow")
    fun patchUnFollowOthers(@Query("targetId") targetId: Int) : Call<FollowResponse>
}