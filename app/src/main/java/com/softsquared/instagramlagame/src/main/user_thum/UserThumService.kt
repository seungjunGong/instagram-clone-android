package com.softsquared.instagramlagame.src.main.user_thum

import com.softsquared.instagramlagame.config.ApplicationClass
import com.softsquared.instagramlagame.src.main.user_thum.models.GetUserThumRequest
import com.softsquared.instagramlagame.src.main.user_thum.models.UserThumResponse
import retrofit2.Call
import retrofit2.Response

class UserThumService (val userThumInterface: UserThumInterface) {

    fun tryGetUserThumbNaill(getUserThumRequest: GetUserThumRequest){
        val userThumRetrofitInterface = ApplicationClass.sRetrofit.create(UserThumRetrofitInterface::class.java)
        userThumRetrofitInterface.getUserThumbnail(getUserThumRequest).enqueue(object : retrofit2.Callback<UserThumResponse> {
            override fun onResponse(
                call: Call<UserThumResponse>,
                response: Response<UserThumResponse>,
            ) {
                userThumInterface.onGetUserThumbnailSuccess(response.body() as UserThumResponse)
            }

            override fun onFailure(call: Call<UserThumResponse>, t: Throwable) {
                userThumInterface.onGetUserThumbnailFailure(t.message ?: "통신 오류")
            }

        })
    }
}
