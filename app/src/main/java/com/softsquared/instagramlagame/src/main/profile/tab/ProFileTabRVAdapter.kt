package com.softsquared.instagramlagame.src.main.profile.tab

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.softsquared.instagramlagame.databinding.GridItemBinding
import com.softsquared.instagramlagame.src.main.user_thum.models.Thumbnail

class ProFileTabRVAdapter(private val thumbnail: List<Thumbnail>): RecyclerView.Adapter<ProFileTabRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: GridItemBinding = GridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(thumbnail[position])
    }

    override fun getItemCount(): Int = thumbnail.size


    inner class ViewHolder(private val binding: GridItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Thumbnail){
            // 아이템 레이아웃에서 정한 값들을 데이터클래스 값으로 넣어줌
                Glide.with(binding.gridItemIv.context)
                    .load(data.thumbnail)
                    .apply(RequestOptions.centerCropTransform())
                    .error(  "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRc1GXYCnH18YeoejkENhnggCRkj4osY0G_RQ&usqp=CAU")
                    .into(binding.gridItemIv)
        }

    }


}