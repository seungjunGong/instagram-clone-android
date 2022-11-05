package com.softsquared.instagramlagame.src.main.profile.edit

import android.os.Bundle
import android.system.Os.bind
import android.view.View
import androidx.annotation.Dimension
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentProfileEditBinding
import com.softsquared.instagramlagame.src.main.profile.ProFileFragmentInterface
import com.softsquared.instagramlagame.src.main.profile.ProFileService
import com.softsquared.instagramlagame.src.main.profile.ProfileFragmentDirections
import com.softsquared.instagramlagame.src.main.profile.edit.models.PatchProFileEditRequest
import com.softsquared.instagramlagame.src.main.profile.edit.models.ProFileEditResponse
import com.softsquared.instagramlagame.src.main.profile.models.ProFileMyDataResponse

class ProFileEditFragment: BaseFragment<FragmentProfileEditBinding>(FragmentProfileEditBinding::bind, com.softsquared.instagramlagame.R.layout.fragment_profile_edit),
        ProFileFragmentInterface, ProFileEditFragmentInterface {

    private val args: ProFileEditFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 내 프로필 정보 받아오기
        binding.profileLoading.mainLoading.visibility = View.VISIBLE
        ProFileService(this).tryGetProFileMyData()


        // 현재 페이지 close
        binding.profileEditCloseBt.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        // 이름 수정
        binding.profileGoEditName.setOnClickListener {
            // 데이터 전달
            val action = ProFileEditFragmentDirections.actionProFileEditFragmentToProFileNameEditFragment(getEditName = binding.editNameEt.text.toString())
            Navigation.findNavController(requireView()).navigate(action)
        }
        // 사용자 이름 수정
        binding.profileGoEditNickName.setOnClickListener {
            val data =
                PatchProFileEditRequest(
                    description = binding.editIntroduceEt.text.toString(),
                    profileUrl = binding.editUrlEt.text.toString(),
                    name = binding.editNameEt.text.toString(),
                    nickname = binding.editNickNameEt.text.toString(),
                    link = binding.editLinkEt.text.toString())
            val action = ProFileEditFragmentDirections.actionProFileEditFragmentToProFileNickNameEditFragment(getEditNickName= data)
            Navigation.findNavController(requireView()).navigate(action)
        }
        // 소개 수정
        binding.profileGoEditIntroduce.setOnClickListener {
            val action = ProFileEditFragmentDirections.actionProFileEditFragmentToProFileIntroduceEditFragment(getEditIntroduce= binding.editIntroduceEt.text.toString())
            Navigation.findNavController(requireView()).navigate(action)
        }
        // 링크 수정
        binding.profileGoEditLink.setOnClickListener{
            val action = ProFileEditFragmentDirections.actionProFileEditFragmentToProFileLinkEditFragment(getEditLink= binding.editLinkEt.text.toString())
            Navigation.findNavController(requireView()).navigate(action)
        }


        // 체크 버튼 클릭시
        binding.profileEditCheckBt.setOnClickListener {
            // 수정 완료시
                val data =
                    PatchProFileEditRequest(
                        description = binding.editIntroduceEt.text.toString(),
                        profileUrl = binding.editUrlEt.text.toString(),
                        name = binding.editNameEt.text.toString(),
                        nickname = binding.editNickNameEt.text.toString(),
                        link = binding.editLinkEt.text.toString())
                ProFileEditService(this).tryPatchProFileEdit(data)
                binding.loadingCheck.utilLoadingCheck.visibility = View.VISIBLE

        }




    }

    override fun onGetProFileMyDataSuccess(response: ProFileMyDataResponse) {
        binding.profileLoading.mainLoading.visibility = View.GONE
        if(response.isSuccess){
            with(response.resultProFileMyData){
                if(profileUrl != ""){
                    binding.editUrlEt.text = profileUrl
                    Glide.with(requireContext()).load(this?.profileUrl).into(binding.profileEditImageIv)
                }
                if(name != ""){
                    binding.editNameEt.text = name
                    binding.editNameTv.setTextSize(Dimension.SP, 15F)
                }
                if(nickname != ""){
                    binding.editNickNameEt.text = nickname
                    binding.editNickNameTv.setTextSize(Dimension.SP, 15F)
                }
                if(description != ""){
                    binding.editIntroduceEt.text = description
                    binding.editIntroduceTv.setTextSize(Dimension.SP, 15F)
                }
                if(link != "") {
                    binding.editLinkCount.text = "1"
                    binding.editLinkEt.text = link
                }


            }
        }
        if (args.getDataType != null){
            when (args.getDataType) {
                "링크" -> {
                    if (args.getEditData != "") {
                        binding.editLinkCount.text = "1"
                        binding.editLinkEt.text = args.getEditData
                    }
                }
                "이름" -> binding.editNameEt.text = args.getEditData
                "닉네임" -> binding.editNickNameEt.text = args.getEditData
                "소개" -> binding.editIntroduceEt.text = args.getEditData
            }
        }

    }

    override fun onGetProFileMyDataFailure(message: String) {
        binding.profileLoading.mainLoading.visibility = View.GONE
        showAlertDialog(requireContext(),"경고!","","","")
    }

    override fun onPatchProFileSuccess(response: ProFileEditResponse) {
        binding.loadingCheck.utilLoadingCheck.visibility = View.GONE
        showCustomToast("수정 되었습니다.")
        val action = ProFileEditFragmentDirections.actionProFileEditFragmentToProfileFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }

    override fun onPatchProFileFailure(message: String) {
        binding.loadingCheck.utilLoadingCheck.visibility = View.GONE
        showCustomToast("오류! 수정이 불가합니다.")
        val action = ProFileEditFragmentDirections.actionProFileEditFragmentToProfileFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }
}