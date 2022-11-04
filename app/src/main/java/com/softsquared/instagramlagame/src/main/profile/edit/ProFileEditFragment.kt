package com.softsquared.instagramlagame.src.main.profile.edit

import android.os.Bundle
import android.view.View
import androidx.annotation.Dimension
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentProfileEditBinding
import com.softsquared.instagramlagame.src.main.profile.ProFileFragmentInterface
import com.softsquared.instagramlagame.src.main.profile.ProFileService
import com.softsquared.instagramlagame.src.main.profile.edit.models.PatchProFileEditRequest
import com.softsquared.instagramlagame.src.main.profile.edit.models.ProFileEditResponse
import com.softsquared.instagramlagame.src.main.profile.models.ProFileMyDataResponse

class ProFileEditFragment: BaseFragment<FragmentProfileEditBinding>(FragmentProfileEditBinding::bind, com.softsquared.instagramlagame.R.layout.fragment_profile_edit),
        ProFileFragmentInterface, ProFileEditFragmentInterface {

    private val args: ProFileEditFragmentArgs by navArgs()
    private var myProFileUrl = ""
    private var myProFilLink = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 내 프로필 정보 받아오기
        binding.profileLoading.mainLoading.visibility = View.VISIBLE
        ProFileService(this).tryGetProFileMyData()




        // 현재 페이지 close
        binding.profileEditCloseBt.setOnClickListener {
            val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction().remove(this).commit()
            fragmentManager.popBackStack()
        }

        // 이름 수정
        binding.profileGoEditName.setOnClickListener {
            // 데이터 전달
            val action = ProFileEditFragmentDirections.actionProFileEditFragmentToProFileNameEditFragment(binding.editNameEt.text)
            Navigation.findNavController(requireView()).navigate(action)
        }
        // 사용자 이름 수정
        binding.profileGoEditNickName.setOnClickListener {
            val action = ProFileEditFragmentDirections.actionProFileEditFragmentToProFileNickNameEditFragment(binding.editNickNameEt.text)
            Navigation.findNavController(requireView()).navigate(action)
        }
        // 소개 수정
        binding.profileGoEditIntroduce.setOnClickListener {
            val action = ProFileEditFragmentDirections.actionProFileEditFragmentToProFileIntroduceEditFragment(binding.editIntroduceEt.text)
            Navigation.findNavController(requireView()).navigate(action)
        }
        // 링크 수정
        binding.profileGoEditLink.setOnClickListener{
            val action = ProFileEditFragmentDirections.actionProFileEditFragmentToProFileLinkEditFragment(myProFileUrl)
            Navigation.findNavController(requireView()).navigate(action)
        }

        binding.profileEditCheckBt.setOnClickListener {

            if(args.getDataType != null) {
                // 수정 완료시
                when (args.getDataType) {
                    "링크" -> if (args.getEditData != "") {
                        binding.editLinkCount.text = "1"
                    }
                    "이름" -> binding.editNameEt.text = args.getEditData
                    "닉네임" -> binding.editNickNameEt.text = args.getEditData
                    "소개" -> binding.editIntroduceEt.text = args.getEditData
                }

                val data = if (args.getDataType == "링크") {
                    PatchProFileEditRequest(
                        binding.editIntroduceEt.text.toString(),
                        args.getEditData,
                        binding.editNameEt.text.toString(),
                        binding.editNickNameEt.text.toString(),
                        myProFilLink)
                } else {
                    PatchProFileEditRequest(binding.editIntroduceEt.text.toString(),
                        myProFilLink,
                        binding.editNameEt.text.toString(),
                        binding.editNickNameEt.text.toString(),
                        myProFileUrl)
                }
                ProFileEditService(this).tryPatchProFileEdit(data)
                showLoadingDialog(requireContext())
            }
        }




    }

    override fun onGetProFileMyDataSuccess(response: ProFileMyDataResponse) {
        binding.profileLoading.mainLoading.visibility = View.GONE
        if(response.isSuccess){
            with(response.resultProFileMyData){
                if(profileUrl != ""){
                    myProFileUrl = profileUrl
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
                    myProFilLink = link
                }


            }


        }
    }

    override fun onGetProFileMyDataFailure(message: String) {
        binding.profileLoading.mainLoading.visibility = View.GONE
        showAlertDialog(requireContext(),"경고!","","","")
    }

    override fun onPatchProFileSuccess(response: ProFileEditResponse) {
        dismissLoadingDialog()
        showCustomToast("수정 되었습니다.")
        val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction().remove(this).commit()
        fragmentManager.popBackStack()
    }

    override fun onPatchProFileFailure(message: String) {
        dismissLoadingDialog()
    }
}