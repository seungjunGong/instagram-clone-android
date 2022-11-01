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
import androidx.navigation.Navigation
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.TabPhoneBinding
import java.util.*
import java.util.regex.Pattern

class PhoneTab : BaseFragment<TabPhoneBinding>(TabPhoneBinding::bind, R.layout.tab_phone) {

    private var nextClick = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                val certificationNumber = binding.signUpPhoneEt.text.toString().replace("-","")
                showCustomToast("$certificationNumber")
                val action = PhoneEmailFragmentDirections.navToIdPasswordFragment()
                Navigation.findNavController(requireActivity(), R.id.signUp_container).navigate(action)
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
            if (!binding.signUpPhoneNext.isClickable){
                with(binding.signUpPhoneNext){
                    nextClick = true
                    isClickable = true
                    background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border_inactive)
                    setTextColor(Color.parseColor("#FFFFFF"))
                    binding.signUpPhoneEtClose.visibility = View.VISIBLE
                }
            }
            // 클릭 비활성화
        } else {
            with(binding.signUpPhoneNext){
                nextClick = false
                isClickable = false
                background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border)
                setTextColor(Color.parseColor("#E7F0FD"))
                binding.signUpPhoneEtClose.visibility = View.GONE
            }
        }
        if (binding.phoneWrongTv.visibility == View.VISIBLE){
            binding.phoneWrongTv.visibility = View.GONE
            binding.signUpPhoneEt.background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.et_login_border)
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