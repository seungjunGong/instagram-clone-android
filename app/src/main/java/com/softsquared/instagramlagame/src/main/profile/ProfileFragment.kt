package com.softsquared.instagramlagame.src.main.profile

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.os.Bundle
import android.system.Os.bind
import android.view.LayoutInflater

import android.view.View
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentProfileBinding
import com.softsquared.instagramlagame.src.main.profile.models.ProFileMyDataResponse

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::bind, R.layout.fragment_profile),
        ProFileFragmentInterface{



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 내 프로필 정보 받아오기
        showLoadingMainDialog(requireContext())
        ProFileService(this).tryGetProFileMyData()


        // 뷰 페이저 탭 레이아웃 연결
        val proFileTabAdapter = ProfileTabVPAdapter(requireActivity())
        binding.profileTabVp.adapter = proFileTabAdapter
        binding.profileTabVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.profileTagTabs, binding.profileTabVp) { tab, position ->
            tab.customView = getTabView(position)
        }.attach()



    }
    // 탭 레이아웃 커스텀
    private fun getTabView(position: Int): View {
        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.profile_tab_item, null, false)
        val ivTab = view.findViewById<ImageView>(R.id.ivTab)

        when (position) {
            0 -> ivTab.setImageResource(R.drawable.ic_profile_tab_post)
            else -> ivTab.setImageResource(R.drawable.ic_profile_tab_tag)
        }

        return view
    }

    override fun onGetProFileMyDataSuccess(response: ProFileMyDataResponse) {
        if(response.isSuccess){
            with(response.resultProFileMyData){

                if (link == ""){
                    if(description == ""){
                        binding.profileUserIntroduceTv.visibility = View.GONE
                    }
                    binding.profileGoMyLinkTv.visibility = View.GONE
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
            dismissLoadingMainDialog()
        } else{
            showAlertDialog(requireContext(),"경고!","","","")
        }

    }

    override fun onGetProFileMyDataFailure(message: String) {
        showAlertDialog(requireContext(),"경고!","","","")
    }
}