package com.softsquared.instagramlagame.src.login

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.Selection.setSelection
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.ApplicationClass.Companion.USER_ID
import com.softsquared.instagramlagame.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.softsquared.instagramlagame.config.ApplicationClass.Companion.sSharedPreferences
import com.softsquared.instagramlagame.config.BaseActivity
import com.softsquared.instagramlagame.databinding.ActivityLoginBinding
import com.softsquared.instagramlagame.src.login.models.LoginResponse
import com.softsquared.instagramlagame.src.login.models.PostLoginRequest
import com.softsquared.instagramlagame.src.main.MainActivity
import com.softsquared.instagramlagame.src.signup.SignUpActivity
import com.softsquared.instagramlagame.src.splash.SplashActivity
import java.util.regex.Pattern

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate),
        LoginActivityInterface{

    private var nextClick = false
    private var togglePassword = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.loginCompleteTv.isClickable = false


        // 자동 로그인
        if (sSharedPreferences.getString(X_ACCESS_TOKEN, null) != null){
            startActivity(Intent(this, SplashActivity::class.java))
            finish()
        }


        binding.loginSignUpTv.setOnClickListener {
            // 회원 가입 화면 전환
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.loginPasswordToggle.setOnClickListener {
            with(binding.loginUserPasswordEt){
                if (togglePassword){
                    InputType.TYPE_TEXT_VARIATION_PASSWORD
                    binding.loginPasswordToggle.setImageResource(R.drawable.ic_hide_togle)
                    togglePassword = false
                } else{
                    inputType = InputType.TYPE_CLASS_TEXT
                    binding.loginPasswordToggle.setImageResource(R.drawable.ic_look_togle)
                    togglePassword = true
                }
                setSelection(binding.loginUserPasswordEt.text.length)
            }
        }

        // 패스워드 완료버튼 클릭
        binding.loginUserPasswordEt.setOnEditorActionListener(getEditerActionListener(binding.loginCompleteTv))


        // 로그인 버튼 클릭
        binding.loginCompleteTv.setOnClickListener {
            binding.loginActivity.isClickable = false
            binding.loginCompleteTv.text = ""
            binding.loginProgressBar.visibility = View.VISIBLE
            changeInActiveNextBt()

            val id = binding.loginUserIdEt.text.toString()
            val password = binding.loginUserPasswordEt.text.toString()

            val sendData = PostLoginRequest(email =  "", nickname = "", password = password, phone = "")
            if (Pattern.matches("^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6})*$", id)) {
                // 이메일 로그인
                val sendData = PostLoginRequest(email =  id, nickname = "", password = password, phone = "")
                LoginService(this).tryPostLogin(sendData)

            } else if((Pattern.matches("^[a-zA-Z0-9._-]{6,18}\$" ,id)) && (("." in id)||("_" in id)||("-" in id))) {
                // 사용자 이름 로그인
                val sendData = PostLoginRequest(email =  "", nickname = id, password = password, phone = "")
                LoginService(this).tryPostLogin(sendData)

            } else{
                // 전화번호 로그인
                val sendData = PostLoginRequest(email =  "", nickname = "", password = password, phone = id)
                LoginService(this).tryPostLogin(sendData)
            }
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
                    changeActiveNextBt()
                }
                // 클릭 비활성화
            } else {
                changeInActiveNextBt()
            }
        }
    }
    private fun changeInActiveNextBt(){
        with(binding.loginCompleteTv){
            nextClick = false
            isClickable = false
            background = resources.getDrawable(R.drawable.bt_login_border)
            setTextColor(Color.parseColor("#E7F0FD"))
        }
    }

    private fun changeActiveNextBt(){
        with(binding.loginCompleteTv){
            nextClick = true
            isClickable = true
            background = resources.getDrawable(R.drawable.bt_login_border_inactive)
            setTextColor(Color.parseColor("#FFFFFF"))
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

    override fun onPostLoginSuccess(response: LoginResponse) {
        binding.loginActivity.isClickable = true
        binding.loginCompleteTv.text = "다음"
        binding.loginProgressBar.visibility = View.GONE
        if(response.message == "요청에 성공하였습니다."){

            // jwt 토큰 저장
            val editor : SharedPreferences.Editor = sSharedPreferences.edit()
            editor.putString(X_ACCESS_TOKEN, response.resultLogin.jwt)
            editor.putInt(USER_ID, response.resultLogin.userIdx)
            editor.apply()

            startActivity(Intent(this,MainActivity::class.java))
            finish()
        } else{
            customAlertDialog()
        }
    }

    override fun onPostLoginFailure(message: String) {
        binding.loginActivity.isClickable = true
        binding.loginCompleteTv.text = "다음"
        binding.loginProgressBar.visibility = View.GONE
        customAlertDialog()
    }

    private fun customAlertDialog(){
        val title = "계정을 찾을 수 없음"
        val content = "${binding.loginUserIdEt.text}에 연결된 계정을\n찾을 수 없습니다. 다른\n전화 번호나 이메일 주소를\n사용해보세요. Instagram\n계정이 없으면 가입할 수\n있습니다."
        val topBt = "다시시도"
        val bottomBt = "가입하기"
        showLoginAlertDialog(this,title,content, topBt, bottomBt)
    }
}