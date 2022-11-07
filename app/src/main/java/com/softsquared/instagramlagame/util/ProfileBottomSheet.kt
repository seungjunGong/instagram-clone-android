package com.softsquared.instagramlagame.util

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.databinding.DialogLoadingBinding
import com.softsquared.instagramlagame.databinding.LayoutBottomSheetBinding

class ProfileBottomSheet(context: Context): BottomSheetDialog(context, R.style.BottomSheetDialogTheme){

    private lateinit var binding: LayoutBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = LayoutBottomSheetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.settingBottomSheetGoSetting.setOnClickListener{
            dismiss()
        }
    }

    override fun show() {
        if(!this.isShowing) super.show()
    }

    override fun dismiss() {
        super.dismiss()
    }
}