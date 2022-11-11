package com.softsquared.instagramlagame.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import com.softsquared.instagramlagame.config.ApplicationClass
import com.softsquared.instagramlagame.databinding.DialogAlertBinding
import com.softsquared.instagramlagame.src.login.LoginActivity

class AlertDialog(context: Context, private val activity: Activity, private val title: String, private val content: String, private val topBt: String, private val bottomBt: String) : Dialog(context) {
    private lateinit var  binding: DialogAlertBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogAlertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.alertTitle.text = title


        if(title == "경고!"){
            binding.alertContentLayout.visibility = View.GONE
            binding.alertTitle.setTextColor(Color.parseColor("#E3242B"))
            binding.alertContent.text = "인증오류 재로그인이 필요합\n니다. 3초 뒤 로그인화면으로 \n넘어갑니다."
            Handler(Looper.getMainLooper()).postDelayed({
                dismiss()
                val intent = Intent(context, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                activity.startActivity(intent)
            }, 3000) //3초 후 실행

        } else if(topBt == "로그아웃"){
            binding.alertTitle.text = title
            binding.alertContent.text = content
            binding.alertTopBt.text = topBt
            binding.alertBottomBt.text = bottomBt
            binding.alertTopBt.setOnClickListener {
                // 로그아웃

                // jwt 토큰 초기환
                val editor : SharedPreferences.Editor = ApplicationClass.sSharedPreferences.edit()
                editor.putString(ApplicationClass.X_ACCESS_TOKEN, null)
                editor.apply()


                val intent = Intent(context, LoginActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP //액티비티 스택제거
                activity.startActivity(intent)
            }
            binding.alertBottomBt.setOnClickListener {
                dismiss()
            }
        }

        else{
            binding.alertContent.text = content
            binding.alertTopBt.text = topBt
            binding.alertBottomBt.text = bottomBt

            binding.alertTopBt.setOnClickListener {
                activity.finish()
            }

            binding.alertBottomBt.setOnClickListener {
                dismiss()
            }
        }
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        window!!.setBackgroundDrawable(ColorDrawable())
        window!!.setDimAmount(0.5f)



    }

    override fun show() {
        if(!this.isShowing) super.show()
    }


}