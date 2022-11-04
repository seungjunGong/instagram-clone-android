package com.softsquared.instagramlagame.src.main.profile.tab

import com.softsquared.instagramlagame.src.main.profile.models.ProFileCompleteResponse

interface ProfileTabFragmentInterface {
    fun onGetProFileCompleteSuccess(response: ProFileCompleteResponse)

    fun onGetProFileCompleteFailure(message: String)
}