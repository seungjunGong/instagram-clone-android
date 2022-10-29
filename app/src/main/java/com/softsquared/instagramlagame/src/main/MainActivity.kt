package com.softsquared.instagramlagame.src.main

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseActivity
import com.softsquared.instagramlagame.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 바텀네비게이션
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.mainBttnav.itemIconTintList = null
        NavigationUI.setupWithNavController(binding.mainBttnav, navController)
    }

}
