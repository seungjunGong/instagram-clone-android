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
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.softsquared.instagramlagame.R

import com.softsquared.instagramlagame.databinding.ActivityMainBinding
import com.softsquared.instagramlagame.src.main.home.HomeViewModel
import com.softsquared.instagramlagame.src.main.profile.models.ResultProFileMyData
import com.softsquared.instagramlagame.src.signup.SignUpService
import com.softsquared.instagramlagame.src.signup.sginup_models.SignUpViewModel

import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation
import jp.wasabeef.glide.transformations.internal.Utils


class MainActivity : AppCompatActivity() {

    // 바텀네비게이션 바 처리를 위함
    lateinit var binding : ActivityMainBinding
    lateinit var checkHomeProfile : HomeViewModel

    var check = true
    private var userImage = "https://firebasestorage.googleapis.com/v0/b/instagramlagame.appspot.com/o/ic_profile.png?alt=media&token=0053a8f4-3cdf-44b7-8dbe-768ac4d4bba4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        applyWhiteColors()

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

        checkHomeProfile = ViewModelProvider(this)[HomeViewModel::class.java]
        //observe를 통해 liveData가 바뀔때마다 체크하는 함수
        checkHomeProfile.checkProfile.observe(this, Observer{
            // 등록 요청
            if(check){
                userImage = it.profileUrl
            }
            check = false
        })

        // 바텀네비게이션
        val navHostFragment = supportFragmentManager.findFragmentById(com.softsquared.instagramlagame.R.id.main_container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.mainBttnav.itemIconTintList = null
        NavigationUI.setupWithNavController(binding.mainBttnav, navController)

        // 프로필 버튼 전환 처리
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if(destination.id == R.id.profileFragment) {
                Glide.with(applicationContext).asBitmap().load("https://firebasestorage.googleapis.com/v0/b/instagramlagame.appspot.com/o/ic_profile.png?alt=media&token=0053a8f4-3cdf-44b7-8dbe-768ac4d4bba4")
                    .apply(bitmapTransform(
                    CropCircleWithBorderTransformation(Utils.toDp(15), Color.BLACK))).into(object : CustomTarget<Bitmap?>() {
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
                Glide.with(applicationContext).asBitmap().load("https://firebasestorage.googleapis.com/v0/b/instagramlagame.appspot.com/o/ic_profile.png?alt=media&token=0053a8f4-3cdf-44b7-8dbe-768ac4d4bba4")
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
            if(destination.id == R.id.reelsFragment) {
                binding.mainBttnav.setBackgroundColor(Color.parseColor("#000000"))
                applyBlackColors()
                setReelsIcons()
            } else{
                binding.mainBttnav.setBackgroundColor(Color.parseColor("#ffffff"))
                applyWhiteColors()
                setReelsOffIcons()
            }
        }



    }

    private fun setReelsIcons(){
        with(binding.mainBttnav.menu){

            getItem(0).icon = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_white_home)
            getItem(1).icon = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_white_search)
            getItem(3).icon = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_white_shopping)
        }
    }

    private fun setReelsOffIcons(){
        with(binding.mainBttnav.menu){

            getItem(0).icon =  ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_bttnav_home)
            getItem(1).icon = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_bttnav_search)
            getItem(3).icon = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_bttnav_shopping)
        }
    }





    // Apply the title/navigation bar color
    private fun applyWhiteColors() {
        window.statusBarColor = Color.parseColor("#FFFFFF")
        window.navigationBarColor = Color.parseColor("#FFFFFF")
    }

    private fun applyBlackColors() {
        window.statusBarColor = Color.parseColor("#000000")
        window.navigationBarColor = Color.parseColor("#000000")
    }



}

