package com.softsquared.instagramlagame.src.signup.phone_email

import android.graphics.Color
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.telephony.PhoneNumberUtils
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.TabPhoneBinding
import com.softsquared.instagramlagame.src.signup.phone_email.models.PhoneEmailFragmentInterface
import com.softsquared.instagramlagame.src.signup.phone_email.models.PhoneEmailResponse
import com.softsquared.instagramlagame.src.signup.phone_email.models.PhoneEmailService
import com.softsquared.instagramlagame.src.signup.sginup_models.PostSignUpRequest
import com.softsquared.instagramlagame.src.signup.sginup_models.SignUpViewModel
import java.util.*
import java.util.regex.Pattern

class PhoneTab : BaseFragment<TabPhoneBinding>(TabPhoneBinding::bind, R.layout.tab_phone),
        PhoneEmailFragmentInterface{

    private var nextClick = false

    lateinit var checkSignUpViewModel: SignUpViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //viewModelProvider에 오너를 requireActivity()로 해주어야합니다.
        checkSignUpViewModel = ViewModelProvider(requireActivity())[SignUpViewModel::class.java]

        binding.signUpPhoneNext.isClickable = false
        // 텍스트 삭제
        binding.signUpPhoneEtClose.setOnClickListener {
            with(binding.signUpPhoneEt){
                setText("")
                setSelection(0)
                isCursorVisible = true
            }
        }

        // 완료버튼 클릭
        binding.signUpPhoneEt.setOnEditorActionListener(getEditerActionListener(binding.signUpPhoneNext))

        // 다음
        binding.signUpPhoneNext.setOnClickListener {
            // 휴대폰 정규식
            if (!Pattern.matches("^01([0|1|6|7|8|9])-?([0-9]{4})-?([0-9]{4})$", binding.signUpPhoneEt.text)) {
                binding.phoneWrongTv.visibility = View.VISIBLE
                binding.signUpPhoneEt.background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border_wrong)
            } else{
                changeInActiveNextBt()
                binding.signUpPhoneNext.text = ""
                binding.signUpPhoneProgressBar.visibility = View.VISIBLE
                val certificationNumber = binding.signUpPhoneEt.text.toString().replace("-","")


                // 전화번호 확인 요청
                PhoneEmailService(this).tryGetPhoneEmail(certificationNumber)
            }
        }

        // 텍스트 입력시마다 호출
        binding.signUpPhoneEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                editTextCheck()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                editTextCheck()
            }
        })
        binding.signUpPhoneEt.addTextChangedListener(
            //전화번호 포맷 변환
            PhoneNumberFormattingTextWatcher())


    }

    private fun editTextCheck(){
        if(binding.signUpPhoneEt.text.toString() != "") {
            // 클릭 활성화
            if (!binding.signUpPhoneNext.isClickable){ changeActiveNextBt()}
            // 클릭 비활성화
        } else { changeInActiveNextBt()}
        if (binding.phoneWrongTv.visibility == View.VISIBLE){
            binding.phoneWrongTv.visibility = View.GONE
            binding.signUpPhoneEt.background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.et_login_border)
        }

    }

    private fun changeInActiveNextBt(){
        with(binding.signUpPhoneNext){
            nextClick = false
            isClickable = false
            background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border)
            setTextColor(Color.parseColor("#E7F0FD"))
            binding.signUpPhoneEtClose.visibility = View.GONE
        }
    }
    private fun changeActiveNextBt(){
        with(binding.signUpPhoneNext){
            nextClick = true
            isClickable = true
            background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border_inactive)
            setTextColor(Color.parseColor("#FFFFFF"))
            binding.signUpPhoneEtClose.visibility = View.VISIBLE
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

    // 요청 성공
    override fun onGetPhoneEmailSuccess(response: PhoneEmailResponse, phone: String) {

        binding.signUpPhoneNext.text = "다음"
        binding.signUpPhoneProgressBar.visibility = View.GONE

        if(response.result == "사용가능한 이메일 혹은 전화번호입니다."){
            val data = PostSignUpRequest(phoneEmail =  phone)
            val action = PhoneEmailFragmentDirections.navToIdPasswordFragment(data)

            Navigation.findNavController(requireActivity(), R.id.signUp_container).navigate(action)

        } else{
            // 존재하는 전화번호일 때
            val title = "다른 계정에서 사용\n중인 전화번\n호입니다"
            val content = "이 핸드폰 번호와 연결된 계정에\n로그인하거나 다른 핸드폰 번호를\n사용하여 새 계정을 만들 수\n있습니다."
            val topBt = "기존 계정으로 로그인"
            val bottomBt = "새로운 계정 만들기"
            showAlertDialog(requireContext(),title,content, topBt, bottomBt)
        }

    }

    override fun onGetPhoneEmailFailure(message: String) {
        binding.signUpPhoneNext.text = "다음"
        binding.signUpPhoneProgressBar.visibility = View.GONE
        showCustomToast("오류 : $message")
    }
}