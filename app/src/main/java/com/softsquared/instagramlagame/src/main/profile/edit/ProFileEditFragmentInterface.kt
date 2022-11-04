package com.softsquared.instagramlagame.src.main.profile.edit

import com.softsquared.instagramlagame.src.main.profile.edit.models.ProFileEditResponse

interface ProFileEditFragmentInterface {

    fun onPatchProFileSuccess(response: ProFileEditResponse)

    fun onPatchProFileFailure(message: String)
}