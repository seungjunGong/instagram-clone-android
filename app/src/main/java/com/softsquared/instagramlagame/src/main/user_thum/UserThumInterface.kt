package com.softsquared.instagramlagame.src.main.user_thum

import com.softsquared.instagramlagame.src.main.user_thum.models.UserThumResponse

interface UserThumInterface {

    fun onGetUserThumbnailSuccess(response: UserThumResponse)

    fun onGetUserThumbnailFailure(message: String)
}