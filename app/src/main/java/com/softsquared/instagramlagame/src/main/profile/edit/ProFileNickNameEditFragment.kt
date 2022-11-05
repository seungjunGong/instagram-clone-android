package com.softsquared.instagramlagame.src.main.profile.edit

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.annotation.Dimension
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentProfileNameEditBinding
import com.softsquared.instagramlagame.databinding.FragmentProfileNicknameEditBinding
import com.softsquared.instagramlagame.src.main.profile.edit.models.PatchProFileEditRequest
import com.softsquared.instagramlagame.src.main.profile.edit.models.ProFileEditResponse
import java.util.regex.Pattern

class ProFileNickNameEditFragment : BaseFragment<FragmentProfileNicknameEditBinding>(FragmentProfileNicknameEditBinding::bind, R.layout.fragment_profile_nickname_edit),
        ProFileEditFragmentInterface{

    private val args: ProFileNickNameEditFragmentArgs by navArgs()
    var isCheck = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileEditCheckBt.isClickable = false

        binding.profileEditCheckBt.setOnClickListener {
            // 데이터 전달
            binding.loadingCheck.utilLoadingCheck.visibility = View.VISIBLE

            val data =PatchProFileEditRequest(
                description = args.getEditNickName.description,
                profileUrl = args.getEditNickName.profileUrl,
                name = args.getEditNickName.name,
                nickname = args.getEditNickName.nickname,
                link = args.getEditNickName.link)
            ProFileEditService(this).tryPatchProFileEdit(data)

        }

        binding.profileNicknameEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                endEditTextCheck()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                editTextCheck()
            }
        })

        binding.profileNicknameEt.setText(args.getEditNickName.nickname)

        binding.profileEditCloseBt.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
    }
    private fun editTextCheck(){
        setUnClick()
        binding.loadingProgressBar.visibility = View.VISIBLE
    }
    private fun endEditTextCheck(){
        binding.loadingProgressBar.visibility = View.GONE
        val name = binding.profileNicknameEt.text
        // 유저 네임 정규식
        if ((Pattern.matches("^[a-zA-Z0-9._-]{6,18}\$" ,name)) && (("." in name)||("_" in name)||("-" in name))) {
            setClick()
        } else{
            showCustomToast("사용자 이름에는 알파벳(a~z, A~Z), 숫자(0~9), 일부 특수 문자(._-)만 포함할 수 있습니다.")

        }
    }

    private fun setClick(){
        binding.profileEditCheckBt.setImageResource(R.drawable.ic_sky_blue_check)
        binding.profileEditCheckBt.isClickable = true
        isCheck = true
    }

    private fun setUnClick(){
        binding.profileEditCheckBt.setImageResource(R.drawable.ic_bright_sky_blue_check)
        binding.profileEditCheckBt.isClickable = false
        isCheck = false
    }

    override fun onPatchProFileSuccess(response: ProFileEditResponse) {
        binding.loadingCheck.utilLoadingCheck.visibility = View.GONE
        when(response.code){
            1000 -> {
                val data = binding.profileNicknameEt.text.toString()
                val action = ProFileNickNameEditFragmentDirections.actionProFileNickNameEditFragmentToProFileEditFragment(getEditData =data, getDataType="닉네임")
                Navigation.findNavController(requireView()).navigate(action)
            }
            7101 -> {
                showAlertDialog(requireContext(),"경고!","","","")
            }
            7900 -> {
                showCustomToast("사용중인 사용자 이름 입니다.")
            }

        }

    }

    override fun onPatchProFileFailure(message: String) {
        binding.loadingCheck.utilLoadingCheck.visibility = View.GONE
        showCustomToast("오류! 수정이 불가합니다.")
    }
}