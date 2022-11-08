package com.softsquared.instagramlagame.src.main.profile

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.system.Os.bind
import android.view.LayoutInflater
import android.view.MotionEvent

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.ApplicationClass
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentProfileBinding
import com.softsquared.instagramlagame.src.main.profile.models.ProFileMyDataResponse
import com.softsquared.instagramlagame.src.main.profile.tab.ProFileTabRVAdapter
import com.softsquared.instagramlagame.src.main.user_thum.UserThumInterface
import com.softsquared.instagramlagame.src.main.user_thum.models.UserThumResponse
import com.softsquared.instagramlagame.src.signup.birthday.BirthDayFragmentDirections
import com.softsquared.instagramlagame.util.GridSpacingItemDecoration


class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::bind, R.layout.fragment_profile),
        ProFileFragmentInterface{

    // 릴스 확인
    private var inReels = false
    // 사람 찾아보기 토글
    private var showRecommendFollow = false
    private var myProFileUrl : String = ""
    private var loading : Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.profileFollowFindLayout.visibility = View.GONE

        // 내 프로필 정보 받아오기
        binding.profileLoading.loadingMainProgressBar.visibility = View.VISIBLE
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

        // 설정
        binding.profilePopUpMenu.setOnClickListener {
            // bottomSheetDialog 객체 생성
            val bottomSheetDialog = BottomSheetDialog(
                requireContext(), R.style.BottomSheetDialogTheme
            )
            // layout_bottom_sheet를 뷰 객체로 생성
            // layout_bottom_sheet를 뷰 객체로 생성
            val bottomSheetView = LayoutInflater.from(requireContext().applicationContext).inflate(
                R.layout.layout_bottom_sheet, null)
            // bottomSheetDialog의 dismiss 버튼 선택시 dialog disappear
            bottomSheetView.findViewById<View>(R.id.setting_bottom_sheet_go_setting).setOnClickListener {
                val action =    ProfileFragmentDirections.actionProfileFragmentToMySettingFragment(binding.profileNickName.text.toString())
                Navigation.findNavController(requireView()).navigate(action)
                bottomSheetDialog.dismiss()
            }
            // bottomSheetDialog 뷰 생성
            bottomSheetDialog.setContentView(bottomSheetView)
            // bottomSheetDialog 호출
            bottomSheetDialog.show()
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
        if(!loading){
            binding.profileLoading.loadingMainProgressBar.visibility = View.GONE
        }
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
            // userid 저장
            val editor : SharedPreferences.Editor = ApplicationClass.sSharedPreferences.edit()
            editor.putInt(ApplicationClass.USER_ID, response.resultProFileMyData.userId)
            editor.apply()
        } else{
            showAlertDialog(requireContext(),"경고!","","","")
        }

    }

    override fun onGetProFileMyDataFailure(message: String) {
        if(!loading){
            binding.profileLoading.loadingMainProgressBar.visibility = View.GONE
        }
        showAlertDialog(requireContext(),"경고!","","","")
    }





}