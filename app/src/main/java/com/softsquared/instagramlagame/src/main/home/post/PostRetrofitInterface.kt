package com.softsquared.instagramlagame.src.main.home.post

import com.softsquared.instagramlagame.src.main.home.post.posting.models.RequestUploadPostingData
import com.softsquared.instagramlagame.src.main.home.post.posting.models.UploadPostingResponse
import com.softsquared.instagramlagame.src.main.home.post.story.models.RequestUploadStory
import com.softsquared.instagramlagame.src.main.home.post.story.models.UploadStoryResponse
import com.softsquared.instagramlagame.src.signup.sginup_models.PostSignUpRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PostRetrofitInterface {

    @POST("/posts")
    fun postUploadPosting(@Body params: RequestUploadPostingData) : Call<UploadPostingResponse>

    @POST("/storys/create")
    fun postUploadStory(@Body params: RequestUploadStory) : Call<UploadStoryResponse>

}