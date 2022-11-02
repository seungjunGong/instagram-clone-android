package com.softsquared.instagramlagame.src.signup.user_name.models

import com.softsquared.instagramlagame.config.ApplicationClass.Companion.sRetrofit
import com.softsquared.instagramlagame.src.signup.SignUpRetrofitInterface
import retrofit2.Call
import retrofit2.Response

class UserNameService(val userNameFragmentInterface: UserNameFragmentInterface) {

    fun tryGetUserName(getUserName: String){
        val signUpRetrofitInterface = sRetrofit.create(SignUpRetrofitInterface::class.java)
        signUpRetrofitInterface.getUserNameCheck(getUserName).enqueue(object : retrofit2.Callback<UserNameResponse>{
            override fun onResponse(
                call: Call<UserNameResponse>,
                response: Response<UserNameResponse>,
            ) {
                userNameFragmentInterface.onGetUserNameSuccess(response.body() as UserNameResponse, getUserName)
            }

            override fun onFailure(call: Call<UserNameResponse>, t: Throwable) {
                userNameFragmentInterface.onGetUserNameFailure(t.message ?: "통신 오류")
            }

        })
    }
}