package com.softsquared.instagramlagame.src.login

import com.softsquared.instagramlagame.src.login.models.LoginResponse

interface LoginActivityInterface {

    fun onPostLoginSuccess(response: LoginResponse)

    fun onPostLoginFailure(message: String)

}