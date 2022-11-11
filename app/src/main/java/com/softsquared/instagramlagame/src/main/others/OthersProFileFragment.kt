package com.softsquared.instagramlagame.src.main.others

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentOthersProfileBinding
import com.softsquared.instagramlagame.src.main.others.follow.FollowInterface
import com.softsquared.instagramlagame.src.main.others.follow.FollowResponse
import com.softsquared.instagramlagame.src.main.others.follow.FollowService
import com.softsquared.instagramlagame.src.main.others.models.ProfileGetDataResponse
import com.softsquared.instagramlagame.src.main.profile.ProfileTabVPAdapter
import com.softsquared.instagramlagame.src.main.profile.edit.ProFileEditFragmentArgs

class OthersProFileFragment : BaseFragment<FragmentOthersProfileBinding>(FragmentOthersProfileBinding::bind, R.layout.fragment_others_profile), OthersProfileFragmentInterface, FollowInterface {

    private val args: OthersProFileFragmentArgs by navArgs()
    private var isFollow: Boolean = false
    private var followCnt = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뷰 페이저 탭 레이아웃 연결
        val othersTabAdapter = OthersTabVPAdapter(requireActivity(), args.userId)
        binding.othersTabVp.adapter = othersTabAdapter
        binding.othersTabVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.othersTabs, binding.othersTabVp) { tab, position ->
            tab.customView = getTabView(position)
        }.attach()

        OthersProfileService(this).tryGetProfileData(args.userNickName)

        binding.profileOthersFollowBt.setOnClickListener {
            if(isFollow){
                FollowService(this).tryPatchUnFollow(args.userId)
            } else{
                FollowService(this).tryPostFollow(args.userId)
            }
        }
        binding.profileOthersBackBt.setOnClickListener {
            Navigation.findNavController(requireView()).navigateUp()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // 백버튼 설정
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                view?.let {
                    Navigation.findNavController(requireView()).navigateUp()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
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

    override fun onGetProfileDataSuccess(response: ProfileGetDataResponse) {
        with(response.resultProfileGetData){
            binding.profileOthersNickName.text = nickname
            Glide.with(requireContext()).load(profileUrl).error("https://firebasestorage.googleapis.com/v0/b/instagramlagame.appspot.com/o/ic_profile.png?alt=media&token=0053a8f4-3cdf-44b7-8dbe-768ac4d4bba4").into(binding.profileImageIv)
            binding.profileUserNameTv.text = name
            binding.profilePostCount.text = postCount.toString()
            followCnt = followerCount
            binding.profileFollowerCount.text = followerCount.toString()
            binding.profileFollowingCount.text = followingCount.toString()
            if(link.isNotEmpty()){
                binding.profileGoMyLinkTv.visibility = View.VISIBLE
                binding.profileGoMyLinkTv.text = link
            }
            if(description.isNotEmpty()){
                binding.profileUserIntroduceTv.visibility =View.VISIBLE
                binding.profileUserIntroduceTv.text = description
            }
            if(followingStatus != "N"){
                binding.profileOthersFollowBt.text = "팔로잉"
                binding.profileOthersFollowBt.background = resources.getDrawable(R.drawable.bright_gray_style)
                binding.profileOthersFollowBt.setTextColor(Color.parseColor("#000000"))
                isFollow = true
            }
        }
    }

    override fun onGetProFileDataFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPostFollowSuccess(response: FollowResponse) {
        binding.profileOthersFollowBt.text = "팔로잉"
        binding.profileOthersFollowBt.background = resources.getDrawable(R.drawable.bright_gray_style)
        binding.profileOthersFollowBt.setTextColor(Color.parseColor("#000000"))
        binding.profileFollowerCount.text = (++followCnt).toString()
        isFollow = true
    }

    override fun onPostFollowFailure(message: String) {

    }

    override fun onPatchUnFollowSuccess(response: FollowResponse) {
        binding.profileOthersFollowBt.text = "팔로우"
        binding.profileOthersFollowBt.background = resources.getDrawable(R.drawable.blue_style)
        binding.profileOthersFollowBt.setTextColor(Color.parseColor("#FFFFFF"))
        binding.profileFollowerCount.text = (--followCnt).toString()
        isFollow = false
    }

    override fun onPatchUnFollowFailure(message: String) {
        TODO("Not yet implemented")
    }
}