package com.softsquared.instagramlagame.src.main.profile

import com.softsquared.instagramlagame.src.main.profile.edit.models.PatchProFileEditRequest
import com.softsquared.instagramlagame.src.main.profile.edit.models.ProFileEditResponse
import com.softsquared.instagramlagame.src.main.profile.models.ProFileCompleteResponse
import com.softsquared.instagramlagame.src.main.profile.models.ProFileMyDataResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH


interface ProFileRetrofitInterface {

    @GET("/profile/getMyData")
    fun getProfileMyData() : Call<ProFileMyDataResponse>

    @GET("/profile/getComplete")
    fun getComplete() : Call<ProFileCompleteResponse>

    @PATCH("/profile/modify")
    fun patchProFileEdit(@Body params: PatchProFileEditRequest): Call<ProFileEditResponse>
}