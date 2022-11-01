package com.softsquared.instagramlagame.src.signup.phone_email

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.tabs.TabLayout
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseActivity
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.ActivitySignupBinding
import com.softsquared.instagramlagame.databinding.FragmentPhoneEmailBinding

class PhoneEmailFragment : BaseFragment<FragmentPhoneEmailBinding>(FragmentPhoneEmailBinding::bind, R.layout.fragment_phone_email) {

    private lateinit var tabEmail: EmailTab
    private lateinit var tabPhone: PhoneTab

    private var navController: NavController? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabPhone = PhoneTab()
        tabEmail = EmailTab()


        // 폰, 이메일 전환
        binding.phoneEmailTabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    0 -> {

                        val action = PhoneEmailFragmentDirections.navToPhoneTab()
                        navController?.navigate(action)
                    }
                    1 -> {
                        val action = PhoneEmailFragmentDirections.navToEmailTab()
                        navController?.navigate(action)
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        // 로그인하러 가기 버튼 클릭시
        binding.signUpLoginTv.setOnClickListener {
            requireActivity().finish()
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val phoneEmailFragment = childFragmentManager.findFragmentById(R.id.phone_email_tab_container) as? NavHostFragment
        navController = phoneEmailFragment?.navController
    }

}