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
import androidx.navigation.Navigation
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.TabEmailBinding
import java.util.regex.Pattern


class EmailTab : BaseFragment<TabEmailBinding>(TabEmailBinding::bind, com.softsquared.instagramlagame.R.layout.tab_email){

    private var nextClick = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



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
                    //이메일 맞음!
                    val certificationEmail = binding.signUpEmailEt.text.toString()
                    showCustomToast("$certificationEmail")
                    val action = PhoneEmailFragmentDirections.navToIdPasswordFragment()
                    Navigation.findNavController(requireActivity(), R.id.signUp_container).navigate(action)
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
            if (!binding.signUpEmailNext.isClickable){
                with(binding.signUpEmailNext){
                    nextClick = true
                    isClickable = true
                    background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border_inactive)
                    setTextColor(Color.parseColor("#FFFFFF"))
                    binding.signUpEmailEtClose.visibility = View.VISIBLE
                }
            }
            // 클릭 비활성화
        } else {
            with(binding.signUpEmailNext){
                nextClick = false
                isClickable = false
                background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border)
                setTextColor(Color.parseColor("#E7F0FD"))
                binding.signUpEmailEtClose.visibility = View.GONE
            }
        }
        if (binding.emailWrongTv.visibility == View.VISIBLE){
            binding.emailWrongTv.visibility = View.GONE
            binding.signUpEmailEt.background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.et_login_border)
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