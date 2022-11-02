package com.softsquared.instagramlagame.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.softsquared.instagramlagame.databinding.DialogAlertBinding

class AlertDialog(context: Context, private val activity: Activity, private val title: String, private val content: String, private val topBt: String, private val bottomBt: String) : Dialog(context) {
    private lateinit var  binding: DialogAlertBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogAlertBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setCanceledOnTouchOutside(false)
        setCancelable(false)
        window!!.setBackgroundDrawable(ColorDrawable())
        window!!.setDimAmount(0.5f)

        binding.alertTitle.text = title
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

    override fun show() {
        if(!this.isShowing) super.show()
    }


}