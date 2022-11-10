package com.softsquared.instagramlagame.src.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.softsquared.instagramlagame.databinding.GridItemBinding
import com.softsquared.instagramlagame.src.main.search.models.ResultSearchThum

class SearchRVAdapter(private val list: ArrayList<ResultSearchThum>): RecyclerView.Adapter<SearchRVAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: GridItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: ResultSearchThum){
            Glide.with(binding.gridItemIv.context)
                .load(data.imgUrl)
                .apply(RequestOptions.centerCropTransform())
                .error("https://firebasestorage.googleapis.com/v0/b/instagramlagame.appspot.com/o/feed_image_sample.PNG?alt=media&token=d83e31ab-c0e6-4546-8820-fab7e1a7e552")
                .into(binding.gridItemIv)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: GridItemBinding = GridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size


}