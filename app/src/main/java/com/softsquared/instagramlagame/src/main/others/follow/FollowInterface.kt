package com.softsquared.instagramlagame.src.main.others.follow

import com.softsquared.instagramlagame.src.main.others.models.ProfileGetDataResponse

interface FollowInterface {

    fun onPostFollowSuccess(response: FollowResponse)

    fun onPostFollowFailure(message: String)

    fun onPatchUnFollowSuccess(response: FollowResponse)

    fun onPatchUnFollowFailure(message: String)
}