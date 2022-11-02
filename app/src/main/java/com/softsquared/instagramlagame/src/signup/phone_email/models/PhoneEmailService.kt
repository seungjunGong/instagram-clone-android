package com.softsquared.instagramlagame.src.signup.phone_email.models

import com.softsquared.instagramlagame.config.ApplicationClass.Companion.sRetrofit
import com.softsquared.instagramlagame.src.signup.SignUpRetrofitInterface
import retrofit2.Call
import retrofit2.Response


class PhoneEmailService (val phoneEmailFragmentInterface: PhoneEmailFragmentInterface) {

    fun tryGetPhoneEmail(getPhoneEmail: String){
        val signUpRetrofitInterface = sRetrofit.create(SignUpRetrofitInterface::class.java)
        signUpRetrofitInterface.getPhoneEmailCheck(getPhoneEmail).enqueue(object : retrofit2.Callback<PhoneEmailResponse>{
            override fun onResponse(
                call: Call<PhoneEmailResponse>,
                response: Response<PhoneEmailResponse>,
            ) {
                phoneEmailFragmentInterface.onGetPhoneEmailSuccess(response.body() as PhoneEmailResponse, getPhoneEmail)
            }

            override fun onFailure(call: Call<PhoneEmailResponse>, t: Throwable) {
                phoneEmailFragmentInterface.onGetPhoneEmailFailure(t.message ?: "통신 오류")
            }

        })
    }
}