package com.softsquared.instagramlagame.src.main.profile.edit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentProfileLinkEditBinding

class ProFileLinkEditFragment : BaseFragment<FragmentProfileLinkEditBinding>(FragmentProfileLinkEditBinding::bind, R.layout.fragment_profile_link_edit) {

    private val args: ProFileLinkEditFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileEditCheckBt.setOnClickListener {
            // 데이터 전달
            val data = binding.profileLinkEt.text.toString()
            val action = ProFileLinkEditFragmentDirections.actionProFileLinkEditFragmentToProFileEditFragment(getEditData =data, getDataType="링크")
            Navigation.findNavController(requireView()).navigate(action)
        }

        binding.profileLinkEt.text = args.getEditData

        binding.profileEditCloseBt.setOnClickListener {
            val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction().remove(this).commit()
            fragmentManager.popBackStack()
        }
    }
}