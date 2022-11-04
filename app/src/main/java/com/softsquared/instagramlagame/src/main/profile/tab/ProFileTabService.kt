package com.softsquared.instagramlagame.src.main.profile.tab

import com.softsquared.instagramlagame.config.ApplicationClass
import com.softsquared.instagramlagame.src.main.profile.ProFileRetrofitInterface
import com.softsquared.instagramlagame.src.main.profile.models.ProFileCompleteResponse
import retrofit2.Call
import retrofit2.Response


class ProFileTabService (val proFileTabFragmentInterface: ProfileTabFragmentInterface) {

    fun tryGetProFileComplete() {
        val proFileRetrofitInterface = ApplicationClass.sRetrofit.create(ProFileRetrofitInterface::class.java)
        proFileRetrofitInterface.getComplete().enqueue(object : retrofit2.Callback<ProFileCompleteResponse>{
            override fun onResponse(
                call: Call<ProFileCompleteResponse>,
                response: Response<ProFileCompleteResponse>,
            ) {
                proFileTabFragmentInterface.onGetProFileCompleteSuccess(response.body() as ProFileCompleteResponse)
            }

            override fun onFailure(call: Call<ProFileCompleteResponse>, t: Throwable) {
                proFileTabFragmentInterface.onGetProFileCompleteFailure(t.message ?: "통신오류")
            }

        })
    }
}