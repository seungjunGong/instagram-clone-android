package com.softsquared.instagramlagame.src.main


import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseActivity
import com.softsquared.instagramlagame.databinding.ActivityMainBinding
import com.softsquared.instagramlagame.src.main.home.HomeVpControllerFragment
import com.softsquared.instagramlagame.src.main.profile.ProfileFragment
import com.softsquared.instagramlagame.src.main.reels.ReelsFragment
import com.softsquared.instagramlagame.src.main.search.SearchFragment
import com.softsquared.instagramlagame.src.main.shopping.ShoppingFragment
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation
import jp.wasabeef.glide.transformations.internal.Utils


class MainActivity : AppCompatActivity() {

    // 바텀네비게이션 바 처리를 위함
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        applyColors()

        // 상태바 글씨 보이는 채로 투명
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                statusBarColor = Color.TRANSPARENT
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }

        // 바텀네비게이션
        val navHostFragment = supportFragmentManager.findFragmentById(com.softsquared.instagramlagame.R.id.main_container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.mainBttnav.itemIconTintList = null
        NavigationUI.setupWithNavController(binding.mainBttnav, navController)

        // 프로필 버튼 전환 처리
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if(destination.id == R.id.profileFragment) {
                Glide.with(applicationContext).asBitmap().load("https://firebasestorage.googleapis.com/v0/b/instagramlagame.appspot.com/o/bttnav_sample.PNG?alt=media&token=3d83e122-ec02-42b3-83d3-f07df915d872")
                    .apply(bitmapTransform(
                    CropCircleWithBorderTransformation(Utils.toDp(20), Color.BLACK))).into(object : CustomTarget<Bitmap?>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        @Nullable transition: Transition<in Bitmap?>?,
                    ) {

                        val profileImage: Drawable = BitmapDrawable(resources, resource)
                        binding.mainBttnav.menu.getItem(4).icon = profileImage
                    }

                    override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
                })
            } else {
                Glide.with(applicationContext).asBitmap().load("https://firebasestorage.googleapis.com/v0/b/instagramlagame.appspot.com/o/bttnav_sample.PNG?alt=media&token=3d83e122-ec02-42b3-83d3-f07df915d872")
                    .circleCrop().into(object : CustomTarget<Bitmap?>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            @Nullable transition: Transition<in Bitmap?>?,
                        ) {

                            val profileImage: Drawable = BitmapDrawable(resources, resource)
                            binding.mainBttnav.menu.getItem(4).icon = profileImage
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {}
                    })
            }
        }



    }




    // Apply the title/navigation bar color
    private fun applyColors() {
        window.navigationBarColor = Color.parseColor("#FFFFFF")
    }



}

