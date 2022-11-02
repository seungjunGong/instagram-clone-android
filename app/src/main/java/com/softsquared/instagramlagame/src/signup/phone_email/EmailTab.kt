package com.softsquared.instagramlagame.src.signup.phone_email

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.TabEmailBinding
import com.softsquared.instagramlagame.src.signup.phone_email.models.PhoneEmailFragmentInterface
import com.softsquared.instagramlagame.src.signup.phone_email.models.PhoneEmailResponse
import com.softsquared.instagramlagame.src.signup.phone_email.models.PhoneEmailService
import com.softsquared.instagramlagame.src.signup.sginup_models.PostSignUpRequest
import com.softsquared.instagramlagame.src.signup.sginup_models.SignUpViewModel
import java.util.regex.Pattern


class EmailTab : BaseFragment<TabEmailBinding>(TabEmailBinding::bind, com.softsquared.instagramlagame.R.layout.tab_email),
        PhoneEmailFragmentInterface {

    private var nextClick = false

    lateinit var checkSignUpViewModel: SignUpViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //viewModelProvider에 오너를 requireActivity()로 해주어야합니다.
        checkSignUpViewModel = ViewModelProvider(requireActivity())[SignUpViewModel::class.java]


        binding.signUpEmailNext.isClickable = false
        // 텍스트 삭제
        binding.signUpEmailEtClose.setOnClickListener {
            with(binding.signUpEmailEt){
                setText("")
                setSelection(0)
                isCursorVisible = true
            }
        }

        // 완료버튼 클릭
        binding.signUpEmailEt.setOnEditorActionListener(getEditerActionListener(binding.signUpEmailNext))

        // 다음
        binding.signUpEmailNext.setOnClickListener {
                // 이메일 정규식
                if (!Pattern.matches("^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6})*$", binding.signUpEmailEt.text)) {
                    //이메일 아님!
                    binding.emailWrongTv.visibility = View.VISIBLE
                    binding.signUpEmailEt.background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border_wrong)
                } else {
                    changeInActiveNextBt()
                    binding.signUpEmailNext.text = ""
                    binding.signUpEmailProgressBar.visibility = View.VISIBLE

                    //이메일 맞음!
                    val certificationEmail = binding.signUpEmailEt.text.toString()

                    // 이메일 확인 요청
                    PhoneEmailService(this).tryGetPhoneEmail(certificationEmail)
                }
        }





        binding.signUpEmailEt.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                editTextCheck()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               editTextCheck()
            }
        })
    }



    private fun editTextCheck(){
        if(binding.signUpEmailEt.text.toString() != "") {
            // 클릭 활성화
            if (!binding.signUpEmailNext.isClickable){ changeActiveNextBt() }
            // 클릭 비활성화
        } else { changeInActiveNextBt() }
        if (binding.emailWrongTv.visibility == View.VISIBLE){
            binding.emailWrongTv.visibility = View.GONE
            binding.signUpEmailEt.background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.et_login_border)
        }
    }

    private fun changeInActiveNextBt(){
        with(binding.signUpEmailNext){
            nextClick = false
            isClickable = false
            background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border)
            setTextColor(Color.parseColor("#E7F0FD"))
            binding.signUpEmailEtClose.visibility = View.GONE
        }
    }

    private fun changeActiveNextBt(){
        with(binding.signUpEmailNext){
            nextClick = true
            isClickable = true
            background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border_inactive)
            setTextColor(Color.parseColor("#FFFFFF"))
            binding.signUpEmailEtClose.visibility = View.VISIBLE
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

    // 이메일 확인
    override fun onGetPhoneEmailSuccess(response: PhoneEmailResponse, email: String) {
        binding.signUpEmailNext.text = "다음"
        binding.signUpEmailProgressBar.visibility = View.GONE




        if(response.result == "사용가능한 이메일 혹은 전화번호입니다."){
            val data = PostSignUpRequest(phoneEmail = email)
            val action = PhoneEmailFragmentDirections.navToIdPasswordFragment(data)
            Navigation.findNavController(requireActivity(), R.id.signUp_container).navigate(action)
        } else{

            val title = "다른 계정에서 사용\n중인 이메일\n주소입니다"
            val content = "이 이메일 주소와 연결된 계정에\n로그인하거나 다른 이메일 주소\n를 사용하여 새 계정을 만들 수\n있습니다."
            val topBt = "기존 계정으로 로그인"
            val bottomBt = "새로운 계정 만들기"
            showAlertDialog(requireContext(),title,content, topBt, bottomBt)
        }
    }

    override fun onGetPhoneEmailFailure(message: String) {
        binding.signUpEmailNext.text = "다음"
        binding.signUpEmailProgressBar.visibility = View.GONE
        showCustomToast("오류 : $message")
    }


}