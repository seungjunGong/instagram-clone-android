package com.softsquared.instagramlagame.src.signup.user_name

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentUserNameBinding
import com.softsquared.instagramlagame.src.signup.agreement.AgreementFragmentArgs
import com.softsquared.instagramlagame.src.signup.phone_email.PhoneEmailFragmentDirections
import com.softsquared.instagramlagame.src.signup.phone_email.models.PhoneEmailService
import com.softsquared.instagramlagame.src.signup.sginup_models.PostSignUpRequest
import com.softsquared.instagramlagame.src.signup.sginup_models.SignUpViewModel
import com.softsquared.instagramlagame.src.signup.user_name.models.UserNameFragmentInterface
import com.softsquared.instagramlagame.src.signup.user_name.models.UserNameResponse
import com.softsquared.instagramlagame.src.signup.user_name.models.UserNameService
import java.util.regex.Pattern

class UserNameFragment : BaseFragment<FragmentUserNameBinding>(FragmentUserNameBinding::bind, R.layout.fragment_user_name),
        UserNameFragmentInterface{

    private var nextClick = false

    lateinit var checkSignUpViewModel: SignUpViewModel

    private val args: UserNameFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //viewModelProvider에 오너를 requireActivity()로 해주어야합니다.
        checkSignUpViewModel = ViewModelProvider(requireActivity())[SignUpViewModel::class.java]

        // 완료버튼 클릭
        binding.signUpUserNameEt.setOnEditorActionListener(getEditerActionListener(binding.signUpUserNameNext))


        binding.signUpUserNameNext.isClickable = false
        // 다음
        binding.signUpUserNameNext.setOnClickListener {
            val name = binding.signUpUserNameEt.text
            // 유저 네임 정규식
            if ((Pattern.matches("^[a-zA-Z0-9._-]{6,18}\$" ,name)) && (("." in name)||("_" in name)||("-" in name))) {
                //유저 네임 맞음!
                val certificationUserName = binding.signUpUserNameEt.text.toString()

                // 유저 네임 확인 요청
                UserNameService(this).tryGetUserName(certificationUserName)


            } else {
                //유저 네임 아님!
                binding.userNameWrongTv.visibility = View.VISIBLE
                binding.signUpUserNameEt.background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border_wrong)
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
        val name = binding.signUpUserNameEt.text.toString()
        if ((Pattern.matches("^[a-zA-Z0-9._-]{6,18}\$" ,name)) && (("." in name)||("_" in name)||("-" in name))) {
            binding.signUpUserNameCorrect.visibility = View.VISIBLE
        } else{
            binding.signUpUserNameCorrect.visibility = View.GONE
        }
        if ( binding.userNameRequestWrongTv.visibility == View.VISIBLE ){
            binding.userNameRequestWrongTv.visibility == View.GONE
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
    override fun onGetUserNameSuccess(response: UserNameResponse, userName: String) {


        if(response.result == "사용가능한 닉네임입니다."){
            val data = PostSignUpRequest(phoneEmail = args.getNext!!.phoneEmail, birth = args.getNext!!.birth, id = args.getNext!!.id, password = args.getNext!!.password, user_name = userName)

            // 데이터 전달
            checkSignUpViewModel.setCheckSignUp(data)
        } else {
            dismissLoadingDialog()
            binding.userNameRequestWrongTv.visibility = View.VISIBLE
        }
    }

    override fun onGetUserNameFailure(message: String) {
        showCustomToast("오류 : $message")
    }
}
