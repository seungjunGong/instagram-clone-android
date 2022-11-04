package com.softsquared.instagramlagame.src.main.profile.edit

import com.softsquared.instagramlagame.config.ApplicationClass
import com.softsquared.instagramlagame.src.main.profile.ProFileFragmentInterface
import com.softsquared.instagramlagame.src.main.profile.ProFileRetrofitInterface
import com.softsquared.instagramlagame.src.main.profile.edit.models.PatchProFileEditRequest
import com.softsquared.instagramlagame.src.main.profile.edit.models.ProFileEditResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProFileEditService  (val proFileEditFragmentInterface: ProFileEditFragmentInterface) {

    fun tryPatchProFileEdit(patchProFileEditRequest: PatchProFileEditRequest){
        val proFileEditRetrofitInterface = ApplicationClass.sRetrofit.create(
            ProFileRetrofitInterface::class.java)
        proFileEditRetrofitInterface.patchProFileEdit(patchProFileEditRequest).enqueue(object :
            Callback<ProFileEditResponse> {
            override fun onResponse(
                call: Call<ProFileEditResponse>,
                response: Response<ProFileEditResponse>,
            ) {
                proFileEditFragmentInterface.onPatchProFileSuccess(response.body() as ProFileEditResponse)
            }

            override fun onFailure(call: Call<ProFileEditResponse>, t: Throwable) {
                proFileEditFragmentInterface.onPatchProFileFailure(t.message ?: "통신 오류")
            }

        })
    }
}