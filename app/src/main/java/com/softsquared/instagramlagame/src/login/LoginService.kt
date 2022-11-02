package com.softsquared.instagramlagame.src.login

import com.softsquared.instagramlagame.config.ApplicationClass
import com.softsquared.instagramlagame.src.login.models.LoginResponse
import com.softsquared.instagramlagame.src.login.models.PostLoginRequest

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService(val loginActivityInterface: LoginActivityInterface) {

    fun tryPostLogin(postLoginRequest: PostLoginRequest){
        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        loginRetrofitInterface.postLogin(postLoginRequest).enqueue(object :
            Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                loginActivityInterface.onPostLoginSuccess(response.body() as LoginResponse)
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginActivityInterface.onPostLoginFailure(t.message ?: "통신 오류")
            }
        })
    }
}