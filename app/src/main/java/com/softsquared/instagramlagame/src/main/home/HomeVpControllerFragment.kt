package com.softsquared.instagramlagame.src.main.home

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.system.Os.bind
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentHomeVpControllerBinding
import com.softsquared.instagramlagame.src.main.MainActivity
import com.softsquared.instagramlagame.src.main.home.post.PostFragment
import com.softsquared.instagramlagame.src.main.home.post.PostFragmentDirections
import com.softsquared.instagramlagame.src.main.home.post.PostStoryFragment


class HomeVpControllerFragment : BaseFragment<FragmentHomeVpControllerBinding>(FragmentHomeVpControllerBinding::bind, R.layout.fragment_home_vp_controller){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.homeVpController.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val homeVPAdapter = HomeVPAdapter(requireActivity())
        binding.homeVpController.adapter = homeVPAdapter

        binding.homeVpController.offscreenPageLimit = 1
        binding.homeVpController.setCurrentItem(1, false)

        binding.homeVpController.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                when(binding.homeVpController.currentItem){
                    0 -> {
                        applyBlackColors()
                        (context as MainActivity).binding.mainBttnav.visibility = View.GONE

                    }
                    1 -> {
                        applyWhiteColors()
                        (context as MainActivity).binding.mainBttnav.visibility = View.VISIBLE
                    }
                }

            }

        })


    }




    override fun onAttach(context: Context) {
        super.onAttach(context)

        // 백버튼 설정
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                view?.let {
                    if (binding.homeVpController.currentItem == 0) {
                        binding.homeVpController.setCurrentItem( binding.homeVpController.currentItem + 1, true)
                    }
                    else {
                        activity!!.finish()
                    }
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }



    // Apply the title/navigation bar color
    private fun applyWhiteColors() {
        requireActivity().window.statusBarColor = Color.parseColor("#FFFFFF");
        requireActivity().window.navigationBarColor = Color.parseColor("#FFFFFF")
    }

    // Apply the title/navigation bar color
    private fun applyBlackColors() {
        requireActivity().window.statusBarColor = Color.parseColor("#000000");
        requireActivity().window.navigationBarColor = Color.parseColor("#000000");
    }



}