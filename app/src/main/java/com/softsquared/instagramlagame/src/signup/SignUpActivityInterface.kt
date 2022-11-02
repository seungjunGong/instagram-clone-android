package com.softsquared.instagramlagame.src.signup

import com.softsquared.instagramlagame.src.signup.sginup_models.SignUpResponse

interface SignUpActivityInterface {

    fun onPostSignUpSuccess(response: SignUpResponse)

    fun onPostSignUpFailure(message: String)
}