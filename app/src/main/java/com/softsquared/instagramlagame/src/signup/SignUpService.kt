package com.softsquared.instagramlagame.src.signup

import com.softsquared.instagramlagame.config.ApplicationClass
import com.softsquared.instagramlagame.src.signup.sginup_models.PostSignUpRequest
import com.softsquared.instagramlagame.src.signup.sginup_models.SignUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpService(val signUpActivityInterface: SignUpActivityInterface) {

    fun tryPostSignUp(postSignUpRequest: PostSignUpRequest){
        val signUpRetrofitInterface = ApplicationClass.sRetrofit.create(SignUpRetrofitInterface::class.java)
        signUpRetrofitInterface.postSignUp(postSignUpRequest).enqueue(object :
            Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                signUpActivityInterface.onPostSignUpSuccess(response.body() as SignUpResponse)
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                signUpActivityInterface.onPostSignUpFailure(t.message ?: "통신 오류")
            }
        })
    }
}