package com.softsquared.instagramlagame.src.main.home.post.posting

import com.softsquared.instagramlagame.src.main.home.post.posting.models.UploadPostingResponse

interface UploadPostingFragmentInterface {

    fun onPostUploadPostingSuccess(response: UploadPostingResponse)

    fun onPostUploadPostingFailure(message: String)
}