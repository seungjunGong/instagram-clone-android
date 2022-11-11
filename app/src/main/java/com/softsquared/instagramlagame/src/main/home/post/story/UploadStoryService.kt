package com.softsquared.instagramlagame.src.main.home.post.story

import com.softsquared.instagramlagame.config.ApplicationClass
import com.softsquared.instagramlagame.src.main.home.post.PostRetrofitInterface
import com.softsquared.instagramlagame.src.main.home.post.story.models.RequestUploadStory
import com.softsquared.instagramlagame.src.main.home.post.story.models.UploadStoryResponse
import retrofit2.Call
import retrofit2.Response

class UploadStoryService (val uploadStoryFragmentInterface: UploadStoryFragmentInterface) {

    fun tryPostUploadStory(getStoryData : RequestUploadStory) {
        val postRetrofitInterface = ApplicationClass.sRetrofit.create(PostRetrofitInterface::class.java)
        postRetrofitInterface.postUploadStory(getStoryData).enqueue(object : retrofit2.Callback<UploadStoryResponse>{
            override fun onResponse(
                call: Call<UploadStoryResponse>,
                response: Response<UploadStoryResponse>,
            ) {
                uploadStoryFragmentInterface.onPostUploadStorySuccess(response.body() as UploadStoryResponse)
            }

            override fun onFailure(call: Call<UploadStoryResponse>, t: Throwable) {
                uploadStoryFragmentInterface.onPostUploadStoryFailure(t.message ?: "통신 오류")
            }

        })
    }
}