package com.softsquared.instagramlagame.src.main


import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.softsquared.instagramlagame.config.BaseActivity
import com.softsquared.instagramlagame.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 바텀네비게이션
        val navHostFragment = supportFragmentManager.findFragmentById(com.softsquared.instagramlagame.R.id.main_container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.mainBttnav.itemIconTintList = null
        NavigationUI.setupWithNavController(binding.mainBttnav, navController)



        Glide.with(applicationContext).asBitmap().load("https://firebasestorage.googleapis.com/v0/b/instagramlagame.appspot.com/o/bttnav_sample.PNG?alt=media&token=3d83e122-ec02-42b3-83d3-f07df915d872")
            .circleCrop().into(object : CustomTarget<Bitmap?>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    @Nullable transition: Transition<in Bitmap?>?,
                ) {

                    val profileImage: Drawable = BitmapDrawable(resources, resource)
                    binding.mainBttnav.menu.getItem(4).icon = profileImage
                }

                override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
            })
        }


}

