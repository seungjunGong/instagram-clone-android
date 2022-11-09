package com.softsquared.instagramlagame.src.main.others

import com.softsquared.instagramlagame.src.main.others.models.ProfileGetDataResponse

interface OthersProfileFragmentInterface {

    fun onGetProfileDataSuccess(response: ProfileGetDataResponse)

    fun onGetProFileDataFailure(message: String)
}