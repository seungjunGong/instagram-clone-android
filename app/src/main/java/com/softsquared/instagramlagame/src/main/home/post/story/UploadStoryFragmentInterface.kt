package com.softsquared.instagramlagame.src.main.home.post.story

import com.softsquared.instagramlagame.src.main.home.post.story.models.UploadStoryResponse

interface UploadStoryFragmentInterface {

    fun onPostUploadStorySuccess(response: UploadStoryResponse)

    fun onPostUploadStoryFailure(message: String)
}