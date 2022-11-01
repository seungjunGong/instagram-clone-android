package com.softsquared.instagramlagame.src.signup.user_name

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.navigation.Navigation
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentUserNameBinding
import com.softsquared.instagramlagame.src.signup.phone_email.PhoneEmailFragmentDirections
import java.util.regex.Pattern

class UserNameFragment : BaseFragment<FragmentUserNameBinding>(FragmentUserNameBinding::bind, R.layout.fragment_user_name) {

    private var nextClick = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 완료버튼 클릭
        binding.signUpUserNameEt.setOnEditorActionListener(getEditerActionListener(binding.signUpUserNameNext))

        // 다음
        binding.signUpUserNameNext.setOnClickListener {
            // 유저 네임 정규식
            if (!Pattern.matches("^[a-zA-Z0-9._-]{6,18}\$" ,binding.signUpUserNameEt.text)) {
                //유저 네임 아님!
                binding.userNameWrongTv.visibility = View.VISIBLE
                binding.signUpUserNameEt.background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border_wrong)
            } else {
                //유저 네임 맞음!
                val certificationUserName = binding.signUpUserNameEt.text.toString()
                showCustomToast("$certificationUserName")
                showLoadingDialog(requireContext())
            }
        }
        binding.signUpUserNameEt.addTextChangedListener(object : TextWatcher {
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
        // 5자 이상
        if(binding.signUpUserNameEt.text.toString().length > 5) {
            // 클릭 활성화
            if (!binding.signUpUserNameNext.isClickable){
                with(binding.signUpUserNameNext){
                    nextClick = true
                    isClickable = true
                    background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border_inactive)
                    setTextColor(android.graphics.Color.parseColor("#FFFFFF"))
                    binding.signUpUserNameNext.visibility = android.view.View.VISIBLE
                }
            }
            // 클릭 비활성화
        } else {
            with(binding.signUpUserNameNext){
                nextClick = false
                isClickable = false
                background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border)
                setTextColor(android.graphics.Color.parseColor("#E7F0FD"))
            }
        }
        if (binding.userNameWrongTv.visibility == View.VISIBLE){
            binding.userNameWrongTv.visibility = View.GONE
            binding.signUpUserNameEt.background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.et_login_border)
        }
        if (Pattern.matches("^[a-zA-Z0-9._-]{6,18}\$" ,binding.signUpUserNameEt.text)) {
            binding.signUpUserNameCorrect.visibility = View.VISIBLE
        } else{
            binding.signUpUserNameCorrect.visibility = View.GONE
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
