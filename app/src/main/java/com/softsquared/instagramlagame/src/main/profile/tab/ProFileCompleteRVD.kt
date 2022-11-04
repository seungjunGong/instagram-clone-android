package com.softsquared.instagramlagame.src.main.profile.tab

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.internal.ContextUtils.getActivity
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.databinding.ProfileCompleteRcvItemBinding
import com.softsquared.instagramlagame.src.main.profile.models.ProFileCompleteViewData
import kotlin.coroutines.coroutineContext

class ProFileCompleteRVD (private val viewData : ArrayList<ProFileCompleteViewData>, val context: Context): RecyclerView.Adapter<ProFileCompleteRVD.ViewHolder>() {

    inner class ViewHolder(private val binding: ProfileCompleteRcvItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ProFileCompleteViewData){
            binding.profileCompleteBt.text = data.buttonTv
            binding.profileCompleteTitleTv.text = data.title
            binding.profileCompleteIv.setImageResource(data.image)
            binding.profileCompleteContentTv.text = data.content
            when (binding.profileCompleteBt.text)
            {
                //"이름 추가" -> { } 프로필 편집 페이지
                "이름 수정" -> {
                    binding.profileCompleteBt.setOnClickListener {

                    }
                    setCompleteSetting()
                } // 프로필 편집 페이지
                //"사진 추가" -> // 사진 bottom seet
                "사진 변경" -> {
                    binding.profileCompleteBt.setOnClickListener {

                    }
                    setCompleteSetting()
                }// 사진 bottom seet
                //"소개 추가" -> // 소개 페이지
                "소개 수정" -> {
                    binding.profileCompleteBt.setOnClickListener {

                    }
                    setCompleteSetting()
                }// 소개 페이지
                //"사람 찾기" -> // 사람 찾아보기 리스트
                "더 찾아보기" -> {
                    binding.profileCompleteBt.setOnClickListener {

                    }
                    setCompleteSetting()}
                } // 사람 찾아보기 리스트

        }
        private fun setCompleteSetting(){
            binding.profileCompleteBt.background = context.getDrawable(com.softsquared.instagramlagame.R.drawable.bright_gray_style)
            binding.profileCompleteBt.setTextColor(Color.parseColor("#000000"))
        }
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProFileCompleteRVD.ViewHolder {
        val binding: ProfileCompleteRcvItemBinding = ProfileCompleteRcvItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(viewData[position])

    }

    override fun getItemCount(): Int = viewData.size



}