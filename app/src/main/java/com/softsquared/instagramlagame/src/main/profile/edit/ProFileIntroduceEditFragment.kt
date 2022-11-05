package com.softsquared.instagramlagame.src.main.profile.edit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentIdPasswordBinding
import com.softsquared.instagramlagame.databinding.FragmentProfileIntroduceEditBinding
import com.softsquared.instagramlagame.src.main.profile.ProfileFragmentDirections

class ProFileIntroduceEditFragment : BaseFragment<FragmentProfileIntroduceEditBinding>(FragmentProfileIntroduceEditBinding::bind, R.layout.fragment_profile_introduce_edit) {

    private val args: ProFileIntroduceEditFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.profileEditCheckBt.setOnClickListener {
            // 데이터 전달
            val data = binding.profileIntroduceEt.text.toString()
            val action = ProFileIntroduceEditFragmentDirections.actionProFileIntroduceEditFragmentToProFileEditFragment(getEditData= data, getDataType= "소개")
            Navigation.findNavController(requireView()).navigate(action)
        }

        binding.profileIntroduceEt.setText(args.getEditIntroduce)

        binding.profileEditCloseBt.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
    }
}