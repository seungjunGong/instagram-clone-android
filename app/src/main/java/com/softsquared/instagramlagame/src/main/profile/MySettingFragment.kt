package com.softsquared.instagramlagame.src.main.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavArgs
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentMySettingBinding
import com.softsquared.instagramlagame.src.signup.birthday.BirthDayFragmentArgs

class MySettingFragment : BaseFragment<FragmentMySettingBinding>(FragmentMySettingBinding::bind, R.layout.fragment_my_setting) {

    private val args: MySettingFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mySettingBackBt.setOnClickListener {
            Navigation.findNavController(requireView()).navigateUp()
            showBttnav()
        }
        binding.mySettingLogoutBt.setOnClickListener {
            showAlertDialog(requireContext(),"${args.userNicName}에서\n로그아웃하시겠어요?","회원님이 저장한 모든 임시\n저장본은 이 기기에서 계속\n이용할 수 있습니다.","로그아웃","취소")
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // 백버튼 설정
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                view?.let {
                    Navigation.findNavController(it).navigateUp()
                    showBttnav()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
}