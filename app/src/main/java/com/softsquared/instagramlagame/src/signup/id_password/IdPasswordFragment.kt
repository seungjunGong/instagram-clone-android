package com.softsquared.instagramlagame.src.signup.id_password

import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
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
import com.softsquared.instagramlagame.databinding.FragmentIdPasswordBinding
import com.softsquared.instagramlagame.src.signup.phone_email.PhoneEmailFragmentDirections
import com.softsquared.instagramlagame.src.signup.sginup_models.PostSignUpRequest
import com.softsquared.instagramlagame.src.signup.sginup_models.SignUpViewModel

class IdPasswordFragment : BaseFragment<FragmentIdPasswordBinding>(FragmentIdPasswordBinding::bind, R.layout.fragment_id_password) {

    private var nextClick = false

    lateinit var checkSignUpViewModel: SignUpViewModel

    private val args: IdPasswordFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 현재 동기화하지 않고 계속하기만 작동함
        binding.numberInactiveNext.isClickable = false

        //viewModelProvider에 오너를 requireActivity()로 해주어야합니다.
        checkSignUpViewModel = ViewModelProvider(requireActivity())[SignUpViewModel::class.java]

        // 텍스트 삭제
        binding.signUpIdEtClose.setOnClickListener {
            with(binding.signUpIdEt){
                setText("")
                setSelection(0)
                isCursorVisible = true
            }
        }

        // 완료버튼 클릭 연락처 비동기화만 클릭 가능
        binding.signUpPasswordEt.setOnEditorActionListener(getEditerActionListener(binding.numberInactiveNext))

        binding.numberInactiveNext.setOnClickListener {
            if(binding.signUpPasswordEt.text.toString().length < 6){
                binding.passwordWrongTv.visibility = View.VISIBLE
                binding.signUpPasswordEt.background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border_wrong)
            } else {
                val certificationId = binding.signUpIdEt.text.toString()
                val certificationPassword = binding.signUpPasswordEt.text.toString()
                // 데이터 전달
                val data = PostSignUpRequest(phoneEmail = args.getPhoneEmail!!.phoneEmail, id = certificationId, password = certificationPassword)

                // 화면 전환
                val action = IdPasswordFragmentDirections.navToBirthDayFragment(data)
                Navigation.findNavController(requireActivity(), R.id.signUp_container).navigate(action)
            }
        }

        // 아이디 입력시
        binding.signUpIdEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                etIdCheck()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                etIdCheck()
            }
        })

        // 패스워드 입력
        binding.signUpPasswordEt.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                etPasswordCheck()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                etPasswordCheck()
            }
        })
    }
    private fun etPasswordCheck(){
        if ((binding.signUpPasswordEt.text.toString() != "") && (binding.signUpIdEt.text.toString() != "")) {
            // 클릭 활성화
            if (!binding.numberInactiveNext.isClickable){
                nextClick = true
                with(binding.numberActiveNext){
                    isClickable = true
                    background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border_inactive)
                    setTextColor(android.graphics.Color.parseColor("#FFFFFF"))
                }
                with(binding.numberInactiveNext){
                    isClickable = true
                    setTextColor(Color.parseColor("#0F9EF1"))
                }
            }
            // 클릭 비활성화
        } else {
            nextClick = false
            with(binding.numberActiveNext){
                isClickable = false
                background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border)
                setTextColor(android.graphics.Color.parseColor("#E7F0FD"))
            }
            with(binding.numberInactiveNext){
                isClickable = false
                setTextColor(Color.parseColor("#87CEFB"))
            }
        }
        if (binding.passwordWrongTv.visibility == View.VISIBLE){
            binding.passwordWrongTv.visibility = View.GONE
            binding.signUpPasswordEt.background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.et_login_border)
        }
    }

    private fun etIdCheck(){
        if ((binding.signUpPasswordEt.text.toString() != "") && (binding.signUpIdEt.text.toString() != "")) {
            // 클릭 활성화
            if (!binding.numberInactiveNext.isClickable){
                nextClick = true
                with(binding.numberActiveNext){
                    isClickable = true
                    background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border_inactive)
                    setTextColor(android.graphics.Color.parseColor("#FFFFFF"))
                }
                with(binding.numberInactiveNext){
                    isClickable = true
                    setTextColor(Color.parseColor("#0F9EF1"))
                }
            }
            // 클릭 비활성화
        } else {
            nextClick = false
            with(binding.numberActiveNext){
                isClickable = false
                background = resources.getDrawable(com.softsquared.instagramlagame.R.drawable.bt_login_border)
                setTextColor(android.graphics.Color.parseColor("#E7F0FD"))
            }
            with(binding.numberInactiveNext){
                isClickable = false
                setTextColor(Color.parseColor("#87CEFB"))
            }
        }
        if(binding.signUpIdEt.text.toString() != ""){
           binding.signUpIdEtClose.visibility = View.VISIBLE
        }
        else{
            binding.signUpIdEtClose.visibility = View.GONE
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