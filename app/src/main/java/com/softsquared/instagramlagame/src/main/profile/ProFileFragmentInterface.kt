package com.softsquared.instagramlagame.src.main.profile

import com.softsquared.instagramlagame.src.main.profile.edit.models.ProFileEditResponse
import com.softsquared.instagramlagame.src.main.profile.models.ProFileMyDataResponse

interface ProFileFragmentInterface {

    fun onGetProFileMyDataSuccess(response: ProFileMyDataResponse)

    fun onGetProFileMyDataFailure(message: String)


}