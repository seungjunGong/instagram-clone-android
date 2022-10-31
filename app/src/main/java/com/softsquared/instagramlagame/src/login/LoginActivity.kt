package com.softsquared.instagramlagame.src.login

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseActivity
import com.softsquared.instagramlagame.databinding.ActivityLoginBinding
import com.softsquared.instagramlagame.src.login.signup.SignUpFragment
import com.softsquared.instagramlagame.src.main.home.HomeFragment

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.loginSignUpTv.setOnClickListener {
            // 회원 가입 화면 전환
        }

        binding.loginPasswordToggle.setOnClickListener {
            // 패스워드 hide, show 구현
        }

        // 패스워드 완료버튼 클릭
        binding.loginUserPasswordEt.setOnEditorActionListener(getEditerActionListener(binding.loginCompleteTv))


        // 로그인 버튼 클릭
        binding.loginCompleteTv.setOnClickListener {
            showCustomToast("로그인 성공!")
        }
    }

    // 완료버튼 클릭시 로그인 버튼 클릭 메서드
    private fun getEditerActionListener(view: View): TextView.OnEditorActionListener{
        return TextView.OnEditorActionListener { textView, actionId, keyEvent ->
            if ((binding.loginUserIdEt.text.toString() != "") && (binding.loginUserPasswordEt.text.toString() != "") && (actionId == EditorInfo.IME_ACTION_DONE)) {
                view.callOnClick()
            }
            false
        }
    }
}