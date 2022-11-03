package com.softsquared.instagramlagame.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import androidx.core.content.ContextCompat.startActivity
import com.softsquared.instagramlagame.databinding.DialogAlertBinding
import com.softsquared.instagramlagame.src.main.MainActivity
import com.softsquared.instagramlagame.src.signup.SignUpActivity

class LoginAlertDialog(context: Context, private val activity: Activity, private val title: String, private val content: String, private val topBt: String, private val bottomBt: String) : Dialog(context) {
    private lateinit var  binding: DialogAlertBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogAlertBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setCanceledOnTouchOutside(false)
        window!!.setBackgroundDrawable(ColorDrawable())
        window!!.setDimAmount(0.5f)

        binding.alertTitle.text = title
        binding.alertContent.text = content
        binding.alertTopBt.text = topBt
        binding.alertBottomBt.text = bottomBt

        binding.alertTopBt.setOnClickListener {
            dismiss()
        }

        binding.alertBottomBt.setOnClickListener {
            dismiss()
            activity.startActivity(Intent(context, SignUpActivity::class.java))
        }

    }

    override fun show() {
        if(!this.isShowing) super.show()
    }


}