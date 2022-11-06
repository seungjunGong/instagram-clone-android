package com.softsquared.instagramlagame.src.main.home.post

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
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentPostBinding
import com.softsquared.instagramlagame.src.main.MainActivity

class PostFragment : BaseFragment<FragmentPostBinding>(FragmentPostBinding::bind, R.layout.fragment_post) {

    private val information = arrayListOf("게시물", "스토리", "릴스", "라이브")


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val postTabAdapter = PostTabVPAdapter(requireActivity())
        binding.postContainer.adapter = postTabAdapter
        binding.postContainer.orientation = ViewPager2.ORIENTATION_HORIZONTAL



        TabLayoutMediator(binding.postTabs, binding.postContainer) { tab, position ->
            tab.text = information[position]
        }.attach()

        binding.postContainer.setCurrentItem(1,false)

    }

}