package com.softsquared.instagramlagame.util

import android.R
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import com.softsquared.instagramlagame.databinding.DialogMainLoadingBinding


class LoadingMainDialog(context: Context) : Dialog(context) {
    private lateinit var binding: DialogMainLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogMainLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        window!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFF")))
        setCanceledOnTouchOutside(false)
        setCancelable(false)
    }

    override fun show() {
        if(!this.isShowing) super.show()
    }
}