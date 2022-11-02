package com.softsquared.instagramlagame.src.signup

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.softsquared.instagramlagame.config.ApplicationClass
import com.softsquared.instagramlagame.config.BaseActivity
import com.softsquared.instagramlagame.databinding.ActivitySignupBinding
import com.softsquared.instagramlagame.src.login.LoginActivityInterface
import com.softsquared.instagramlagame.src.login.LoginService
import com.softsquared.instagramlagame.src.login.models.LoginResponse
import com.softsquared.instagramlagame.src.login.models.PostLoginRequest
import com.softsquared.instagramlagame.src.main.MainActivity
import com.softsquared.instagramlagame.src.signup.sginup_models.PostSignUpRequest
import com.softsquared.instagramlagame.src.signup.sginup_models.SignUpResponse
import com.softsquared.instagramlagame.src.signup.sginup_models.SignUpViewModel


class SignUpActivity : BaseActivity<ActivitySignupBinding>(ActivitySignupBinding::inflate),
        SignUpActivityInterface, LoginActivityInterface{

    lateinit var checkSignUpViewModel: SignUpViewModel
    var check = true
    var userData = PostSignUpRequest()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        checkSignUpViewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        //observe를 통해 liveData가 바뀔때마다 체크하는 함수
        checkSignUpViewModel.checkSignUp.observe(this, Observer{
                // 등록 요청
                if(check){
                    showLoadingDialog(this)
                    checkSignUpViewModel.setCheckSignUp(it)

                    SignUpService(this).tryPostSignUp(it)
                    userData = it
                }
                check = false
        })

    }

    override fun onPostSignUpSuccess(response: SignUpResponse) {
        check = true

        if(response.isSuccess){

            // 로그인 요청
            LoginService(this).tryPostLogin(PostLoginRequest(email = "", nickname = userData.user_name, password = userData.password, phone =  ""))

        }
    }

    override fun onPostSignUpFailure(message: String) {
        check = true
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onPostLoginSuccess(response: LoginResponse) {
        dismissLoadingDialog()
        if(response.message == "요청에 성공하였습니다."){

            // jwt 토큰 저장
            val editor : SharedPreferences.Editor = ApplicationClass.sSharedPreferences.edit()
            editor.putString(ApplicationClass.X_ACCESS_TOKEN, response.resultLogin.jwt)
            editor.apply()

            val i = Intent(this, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        } else {
            showCustomToast("오류!")
        }
    }

    override fun onPostLoginFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}