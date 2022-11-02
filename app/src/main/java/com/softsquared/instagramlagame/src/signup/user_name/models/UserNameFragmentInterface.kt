package com.softsquared.instagramlagame.src.signup.user_name.models

import com.softsquared.instagramlagame.src.signup.phone_email.models.PhoneEmailResponse

interface UserNameFragmentInterface {

    fun onGetUserNameSuccess(response: UserNameResponse, userName: String)

    fun onGetUserNameFailure(message: String)

}