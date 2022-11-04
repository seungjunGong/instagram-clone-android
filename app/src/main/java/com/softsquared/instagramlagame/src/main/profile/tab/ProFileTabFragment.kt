package com.softsquared.instagramlagame.src.main.profile.tab

import android.os.Bundle
import android.util.Log.d
import android.view.View
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentProfileTabItemBinding
import com.softsquared.instagramlagame.src.main.profile.models.ProFileCompleteResponse
import com.softsquared.instagramlagame.src.main.profile.models.ProFileCompleteViewData


class ProFileTabFragment(type : String) : BaseFragment<FragmentProfileTabItemBinding>(FragmentProfileTabItemBinding::bind, com.softsquared.instagramlagame.R.layout.fragment_profile_tab_item),
        ProfileTabFragmentInterface{

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        ProFileTabService(this).tryGetProFileComplete()

    }

    override fun onGetProFileCompleteSuccess(response: ProFileCompleteResponse) {

        var viewData = ArrayList<ProFileCompleteViewData>()

        with(response.resultProFileComplete){
            if (!(name && profileImg && description && follow)){
                viewData.apply {
                    if(!name){
                        add(ProFileCompleteViewData("이름 추가", R.drawable.ic_profile_name_add,"이름추가","친구들이 회원님을 알아볼 수 있도록\n이름과 성을 모두 추가하세요."))
                    }
                    if(!profileImg){
                        add(ProFileCompleteViewData("사진 추가", R.drawable.ic_profile_image_add, "프로필 사진 추가", "Instagram에서 회원님을 나타낼\n프로필 사진을 선택하세요."))
                    }
                    if(!description){
                        add(ProFileCompleteViewData("소개 추가", R.drawable.ic_profile_introduce_add, "소개 추가","팔로워에게 회원님에 대해 간단히\n소개해주세요."))
                    }
                    if(!follow){
                        add(ProFileCompleteViewData("사람 찾기", R.drawable.ic_profile_follow_add, "팔로우할 사람 찾기", "관심 있는 사람 및 관심사를\n팔로우하세요."))
                    }
                    if(name){
                        add(ProFileCompleteViewData("이름 수정", R.drawable.ic_profile_name_add_correct,"이름추가","친구들이 회원님을 알아볼 수 있도록\n이름과 성을 모두 추가하세요."))
                    }
                    if(profileImg){
                        add(ProFileCompleteViewData("사진 변경", R.drawable.ic_profile_image_add_correct, "프로필 사진 추가", "Instagram에서 회원님을 나타낼\n프로필 사진을 선택하세요."))
                    }
                    if(description){
                        add(ProFileCompleteViewData("소개 수정", R.drawable.ic_profile_introduce_add_correct, "소개 추가","팔로워에게 회원님에 대해 간단히\n소개해주세요."))
                    }
                    if(follow){
                        add(ProFileCompleteViewData("더 찾아보기", R.drawable.ic_profile_follow_add_correct, "팔로우할 사람 찾기", "관심 있는 사람 및 관심사를\n팔로우하세요."))
                    }
                }
                // 프로필 완성 레이아웃
                val recyclerViewAdapter = ProFileCompleteRVD(viewData, requireContext())
                binding.profileCompleteRv.adapter = recyclerViewAdapter
            } else{
                binding.profileVpCompleteProfile.visibility = View.GONE
            }
        }




    }

    override fun onGetProFileCompleteFailure(message: String) {

    }


}