package com.softsquared.instagramlagame.src.main.profile

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.graphics.Color
import android.os.Bundle
import android.system.Os.bind
import android.view.LayoutInflater
import android.view.MotionEvent

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentProfileBinding
import com.softsquared.instagramlagame.src.main.profile.models.ProFileMyDataResponse
import com.softsquared.instagramlagame.src.main.profile.models.ResultProFileMyData
import com.softsquared.instagramlagame.src.signup.birthday.BirthDayFragmentDirections
import kotlinx.coroutines.NonDisposableHandle.parent

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::bind, R.layout.fragment_profile),
        ProFileFragmentInterface{

    // 릴스 확인
    private var inReels = false
    // 사람 찾아보기 토글
    private var showRecommendFollow = false
    private var myProFileUrl : String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.profileFollowFindLayout.visibility = View.GONE

        // 내 프로필 정보 받아오기
        binding.profileLoading.mainLoading.visibility = View.VISIBLE
        ProFileService(this).tryGetProFileMyData()


        // 뷰 페이저 탭 레이아웃 연결
        val proFileTabAdapter = ProfileTabVPAdapter(requireActivity(), inReels)
        binding.profileTabVp.adapter = proFileTabAdapter
        binding.profileTabVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.profileTagTabs, binding.profileTabVp) { tab, position ->
            tab.customView = getTabView(position)
        }.attach()


        // 프로필 편집
        binding.profileGoEditBt.setOnClickListener {
            with(binding){
                val action = ProfileFragmentDirections.navToProFileEditFragment()
                Navigation.findNavController(requireView()).navigate(action)
            }
        }

        // 사람 찾아보기 toggle
        binding.profileMorePersonBt.setOnClickListener {
            showRecommendFollow = if(showRecommendFollow){
                binding.profileMorePersonBt.setImageResource(R.drawable.ic_profile_add_follow_close)
                binding.profileFollowFindLayout.visibility = View.GONE
                binding.profileWholeScroll.invalidate()
                false
            } else{
                binding.profileMorePersonBt.setImageResource(R.drawable.ic_profile_add_follow)
                binding.profileFollowFindLayout.visibility = View.VISIBLE
                binding.profileWholeScroll.invalidate()
                true
            }
        }
    }
    // 탭 레이아웃 커스텀
    private fun getTabView(position: Int): View {
        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.profile_tab_item, null, false)
        val ivTab = view.findViewById<ImageView>(R.id.ivTab)

        // 릴스가 있으면 릴스 탭 추가
        if(inReels){
            when (position) {
                0 -> ivTab.setImageResource(R.drawable.ic_profile_tab_post)
                1 -> ivTab.setImageResource(R.drawable.ic_profile_tab_reels)
                else -> ivTab.setImageResource(R.drawable.ic_profile_tab_tag) }
        } else{
            when (position) {
                0 -> ivTab.setImageResource(R.drawable.ic_profile_tab_post)
                else -> ivTab.setImageResource(R.drawable.ic_profile_tab_tag)
            }
        }
        return view
    }

    override fun onGetProFileMyDataSuccess(response: ProFileMyDataResponse) {
        binding.profileLoading.mainLoading.visibility = View.GONE
        if(response.isSuccess){
            with(response.resultProFileMyData){
                myProFileUrl = profileUrl
                if (link != ""){
                    if(description != ""){
                        binding.profileUserIntroduceTv.visibility = View.VISIBLE
                    }
                    binding.profileGoMyLinkTv.visibility = View.VISIBLE
                }
                if(profileUrl != ""){
                    Glide.with(requireContext()).load(profileUrl).into(binding.profileImageIv)
                }
                with(binding){
                    profileUserNameTv.text = name
                    profileNickName.text = nickname
                    profileUserIntroduceTv.text = description
                    profileGoMyLinkTv.text = link
                    profilePostCount.text = postCount.toString()
                    profileFollowerCount.text = followerCount.toString()
                    profileFollowingCount.text = followingCount.toString()
                }
            }
        } else{
            showAlertDialog(requireContext(),"경고!","","","")
        }

    }

    override fun onGetProFileMyDataFailure(message: String) {
        binding.profileLoading.mainLoading.visibility = View.GONE
        showAlertDialog(requireContext(),"경고!","","","")
    }

}