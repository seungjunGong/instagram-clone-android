package com.softsquared.instagramlagame.src.main.profile.tab

import android.graphics.Color
import android.os.Bundle
import android.util.Log.d
import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.ApplicationClass
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentProfileTabItemBinding
import com.softsquared.instagramlagame.src.main.others.OthersProFileFragmentDirections
import com.softsquared.instagramlagame.src.main.profile.ProfileFragmentDirections
import com.softsquared.instagramlagame.src.main.profile.models.ProFileCompleteResponse
import com.softsquared.instagramlagame.src.main.profile.models.ProFileCompleteViewData
import com.softsquared.instagramlagame.src.main.user_thum.UserThumInterface
import com.softsquared.instagramlagame.src.main.user_thum.UserThumService
import com.softsquared.instagramlagame.src.main.user_thum.models.UserThumResponse
import com.softsquared.instagramlagame.util.GridSpacingItemDecoration


class ProFileTabFragment(val type : String) : BaseFragment<FragmentProfileTabItemBinding>(FragmentProfileTabItemBinding::bind, com.softsquared.instagramlagame.R.layout.fragment_profile_tab_item),
        ProfileTabFragmentInterface, UserThumInterface{

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        ProFileTabService(this).tryGetProFileComplete()
        when(type){
            "post" -> {
                val id = ApplicationClass.sSharedPreferences.getInt(ApplicationClass.USER_ID, -1)
                UserThumService(this).tryGetUserThumbNaill(id,0)
            }
            "tag" ->{
                binding.profileTabZeroTagItem.visibility = View.VISIBLE
            }
        }

    }

    override fun onGetProFileCompleteSuccess(response: ProFileCompleteResponse) {

        var viewData = ArrayList<ProFileCompleteViewData>()
        var count = 0
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
                        count++
                        add(ProFileCompleteViewData("이름 수정", R.drawable.ic_profile_name_add_correct,"이름추가","친구들이 회원님을 알아볼 수 있도록\n이름과 성을 모두 추가하세요."))
                    }
                    if(profileImg){
                        count++
                        add(ProFileCompleteViewData("사진 변경", R.drawable.ic_profile_image_add_correct, "프로필 사진 추가", "Instagram에서 회원님을 나타낼\n프로필 사진을 선택하세요."))
                    }
                    if(description){
                        count++
                        add(ProFileCompleteViewData("소개 수정", R.drawable.ic_profile_introduce_add_correct, "소개 추가","팔로워에게 회원님에 대해 간단히\n소개해주세요."))
                    }
                    if(follow){
                        count++
                        add(ProFileCompleteViewData("더 찾아보기", R.drawable.ic_profile_follow_add_correct, "팔로우할 사람 찾기", "관심 있는 사람 및 관심사를\n팔로우하세요."))
                    }
                }
                // 프로필 완성 레이아웃
                val recyclerViewAdapter = ProFileCompleteRVD(viewData, requireContext())
                binding.profileCompleteRv.adapter = recyclerViewAdapter
                binding.profileVpCompleteProfileCountTag.text = "$count/4개"
                if(count < 2){
                    binding.profileVpCompleteProfileCountTag.setTextColor(Color.parseColor("#ff7f00"))
                }

            } else{
                binding.profileVpCompleteProfile.visibility = View.GONE
            }
        }
    }

    override fun onGetProFileCompleteFailure(message: String) {

    }

    override fun onGetUserThumbnailSuccess(response: UserThumResponse) {
        if (response.resultUserThum.thumbnailList.isEmpty()) {
            binding.profileTabZeroPostItem.visibility = View.VISIBLE
        } else{
            binding.profileTabRcv.visibility = View.VISIBLE

            val spanCount = 3 // 3 columns

            val spacing = 10 // 50px

            val includeEdge = false
            binding.profileTabRcv.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
            val layoutManager = GridLayoutManager(requireContext(), 3)
            binding.profileTabRcv.setHasFixedSize(true)
            binding.profileTabRcv.layoutManager = layoutManager
            val proFileTabRVD = ProFileTabRVAdapter(response.resultUserThum.thumbnailList)
            binding.profileTabRcv.adapter = proFileTabRVD

            proFileTabRVD.setFeedItemClickListener(object : ProFileTabRVAdapter.FeedItemClickListener{
                override fun onItemClick(postId: Int) {
                    val action =
                        ProfileFragmentDirections.actionProfileFragmentToPostListFragment(postId)
                    Navigation.findNavController(requireView()).navigate(action)
                }
            })
        }
    }

    override fun onGetUserThumbnailFailure(message: String) {
        TODO("Not yet implemented")
    }

    // 유저 썸네일 받아오기



}