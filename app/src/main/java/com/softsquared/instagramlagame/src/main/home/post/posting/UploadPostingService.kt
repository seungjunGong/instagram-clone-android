package com.softsquared.instagramlagame.src.main.home.post.posting

import com.softsquared.instagramlagame.config.ApplicationClass
import com.softsquared.instagramlagame.src.main.home.post.PostRetrofitInterface
import com.softsquared.instagramlagame.src.main.home.post.posting.models.RequestUploadPostingData
import com.softsquared.instagramlagame.src.main.home.post.posting.models.UploadPostingResponse
import retrofit2.Call
import retrofit2.Response

class UploadPostingService (val uploadPostingFragmentInterface: UploadPostingFragmentInterface) {

    fun tryPostUploadPosting(getPostingData: RequestUploadPostingData){
        val postRetrofitInterface = ApplicationClass.sRetrofit.create(PostRetrofitInterface::class.java)
        postRetrofitInterface.postUploadPosting(getPostingData).enqueue(object : retrofit2.Callback<UploadPostingResponse>{
            override fun onResponse(
                call: Call<UploadPostingResponse>,
                response: Response<UploadPostingResponse>,
            ) {
                uploadPostingFragmentInterface.onPostUploadPostingSuccess(response.body() as UploadPostingResponse)
            }

            override fun onFailure(call: Call<UploadPostingResponse>, t: Throwable) {
                uploadPostingFragmentInterface.onPostUploadPostingFailure(t.message ?: "통신 오류")
            }

        })
    }
}