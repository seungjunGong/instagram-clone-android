package com.softsquared.instagramlagame.src.main.profile.edit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentProfileNameEditBinding

class ProFileNameEditFragment : BaseFragment<FragmentProfileNameEditBinding>(FragmentProfileNameEditBinding::bind, R.layout.fragment_profile_name_edit) {

    private val args: ProFileNameEditFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileEditCheckBt.setOnClickListener {
            // 데이터 전달
            val data = binding.profileNameEt.text.toString()
            val action = ProFileNameEditFragmentDirections.actionProFileNameEditFragmentToProFileEditFragment(getEditData =data, getDataType="이름")
            Navigation.findNavController(requireView()).navigate(action)
        }

        binding.profileNameEt.text = args.getEditData

        binding.profileEditCloseBt.setOnClickListener {
            val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction().remove(this).commit()
            fragmentManager.popBackStack()
        }
    }
}