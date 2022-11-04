package com.softsquared.instagramlagame.src.main.profile

import com.softsquared.instagramlagame.config.ApplicationClass
import com.softsquared.instagramlagame.src.main.profile.edit.models.PatchProFileEditRequest
import com.softsquared.instagramlagame.src.main.profile.edit.models.ProFileEditResponse
import com.softsquared.instagramlagame.src.main.profile.models.ProFileMyDataResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProFileService (val proFileFragmentInterface: ProFileFragmentInterface) {

    fun tryGetProFileMyData() {
        val proFileRetrofitInterface = ApplicationClass.sRetrofit.create(ProFileRetrofitInterface::class.java)
        proFileRetrofitInterface.getProfileMyData().enqueue(object : retrofit2.Callback<ProFileMyDataResponse> {
            override fun onResponse(
                call: Call<ProFileMyDataResponse>,
                response: Response<ProFileMyDataResponse>,
            ) {
                proFileFragmentInterface.onGetProFileMyDataSuccess(response.body() as ProFileMyDataResponse)
            }

            override fun onFailure(call: Call<ProFileMyDataResponse>, t: Throwable) {
                proFileFragmentInterface.onGetProFileMyDataFailure(t.message ?: "통신 오류")
            }

        })
    }

}