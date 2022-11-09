package com.softsquared.instagramlagame.src.main.others

import com.softsquared.instagramlagame.config.ApplicationClass
import com.softsquared.instagramlagame.src.main.others.models.ProfileGetDataResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.create

class OthersProfileService (val othersProfileFragmentInterface: OthersProfileFragmentInterface) {

    fun tryGetProfileData(nickName: String) {
        val othersProfileRetrofitInterface = ApplicationClass.sRetrofit.create(OthersProfileRetrofitInterface::class.java)
        othersProfileRetrofitInterface.getProfileOthersData(nickname = nickName).enqueue(object : retrofit2.Callback<ProfileGetDataResponse>{
            override fun onResponse(
                call: Call<ProfileGetDataResponse>,
                response: Response<ProfileGetDataResponse>,
            ) {
                othersProfileFragmentInterface.onGetProfileDataSuccess(response.body() as ProfileGetDataResponse)
            }

            override fun onFailure(call: Call<ProfileGetDataResponse>, t: Throwable) {
                othersProfileFragmentInterface.onGetProFileDataFailure(t.message ?: "통신 오류")
            }

        })
    }

}