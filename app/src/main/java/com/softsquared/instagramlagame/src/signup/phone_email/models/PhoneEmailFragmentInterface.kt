package com.softsquared.instagramlagame.src.signup.phone_email.models

import com.softsquared.instagramlagame.src.signup.phone_email.models.PhoneEmailResponse

interface PhoneEmailFragmentInterface {

    fun onGetPhoneEmailSuccess(response: PhoneEmailResponse, phoneEmail: String)

    fun onGetPhoneEmailFailure(message: String)

}