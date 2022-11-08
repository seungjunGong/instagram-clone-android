package com.softsquared.instagramlagame.src.main.user_thum

import com.softsquared.instagramlagame.src.main.profile.models.ProFileCompleteResponse
import com.softsquared.instagramlagame.src.main.user_thum.models.GetUserThumRequest
import com.softsquared.instagramlagame.src.main.user_thum.models.UserThumResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface UserThumRetrofitInterface {

    @GET("/posts/user/thumbnail")
    fun getUserThumbnail(@Query("body") params: GetUserThumRequest) : Call<UserThumResponse>
}