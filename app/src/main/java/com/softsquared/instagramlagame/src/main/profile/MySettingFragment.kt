package com.softsquared.instagramlagame.src.main.profile

import android.os.Bundle
import android.view.View
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
            val action = MySettingFragmentDirections.actionMySettingFragmentToProfileFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }
        binding.mySettingLogoutBt.setOnClickListener {
            showAlertDialog(requireContext(),"${args.userNicName}에서\n로그아웃하시겠어요?","회원님이 저장한 모든 임시\n저장본은 이 기기에서 계속\n이용할 수 있습니다.","로그아웃","취소")
        }
    }
}