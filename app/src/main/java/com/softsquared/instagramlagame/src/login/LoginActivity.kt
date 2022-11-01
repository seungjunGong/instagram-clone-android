package com.softsquared.instagramlagame.src.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseActivity
import com.softsquared.instagramlagame.databinding.ActivityLoginBinding
import com.softsquared.instagramlagame.src.signup.SignUpActivity
import com.softsquared.instagramlagame.src.signup.phone_email.PhoneEmailFragment

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    private var nextClick = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.loginSignUpTv.setOnClickListener {
            // 회원 가입 화면 전환
            startActivity(Intent(this, SignUpActivity::class.java))
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

        // 버튼 클릭시
        binding.loginUserIdEt.addTextChangedListener(textWatcher)
        binding.loginUserPasswordEt.addTextChangedListener(textWatcher)

    }

    // edittext 실시간 변경 확인
    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if((binding.loginUserIdEt.text.toString() != "") && (binding.loginUserPasswordEt.text.toString() != "")){
                // 클릭 활성화
                if (!binding.loginCompleteTv.isClickable){
                    with(binding.loginCompleteTv){
                        nextClick = true
                        isClickable = true
                        background = resources.getDrawable(R.drawable.bt_login_border_inactive)
                        setTextColor(Color.parseColor("#FFFFFF"))
                    }
                }
                // 클릭 비활성화
            } else {
                with(binding.loginCompleteTv){
                    nextClick = false
                    isClickable = false
                    background = resources.getDrawable(R.drawable.bt_login_border)
                    setTextColor(Color.parseColor("#E7F0FD"))
                }
            }
        }
    }

    // 완료버튼 클릭시 로그인 버튼 클릭 메서드
    private fun getEditerActionListener(view: View): TextView.OnEditorActionListener{
        return TextView.OnEditorActionListener { textView, actionId, keyEvent ->
            if ((nextClick) && (actionId == EditorInfo.IME_ACTION_DONE)) {
                view.callOnClick()
            }
            false
        }
    }
}