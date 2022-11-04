package com.softsquared.instagramlagame.src.main.profile.edit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentProfileNameEditBinding
import com.softsquared.instagramlagame.databinding.FragmentProfileNicknameEditBinding

class ProFileNickNameEditFragment : BaseFragment<FragmentProfileNicknameEditBinding>(FragmentProfileNicknameEditBinding::bind, R.layout.fragment_profile_nickname_edit) {

    private val args: ProFileNickNameEditFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileEditCheckBt.setOnClickListener {
            // 데이터 전달
            val data = binding.profileNicknameEt.text.toString()
            val action = ProFileNickNameEditFragmentDirections.actionProFileNickNameEditFragmentToProFileEditFragment(getEditData =data, getDataType="닉네임")
            Navigation.findNavController(requireView()).navigate(action)
        }

        binding.profileNicknameEt.text = args.getEditData

        binding.profileEditCloseBt.setOnClickListener {
            val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction().remove(this).commit()
            fragmentManager.popBackStack()
        }
    }
}