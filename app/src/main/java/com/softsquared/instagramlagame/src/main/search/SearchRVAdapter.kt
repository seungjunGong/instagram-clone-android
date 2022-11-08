package com.softsquared.instagramlagame.src.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.softsquared.instagramlagame.databinding.GridItemBinding

class SearchRVAdapter(private val list: ArrayList<String>): RecyclerView.Adapter<SearchRVAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: GridItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: String){
            Glide.with(binding.gridItemIv.context)
                .load(data)
                .apply(RequestOptions.centerCropTransform())
                .error("https://firebasestorage.googleapis.com/v0/b/instagramlagame.appspot.com/o/bttnav_sample.PNG?alt=media&token=3d83e122-ec02-42b3-83d3-f07df915d872")
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