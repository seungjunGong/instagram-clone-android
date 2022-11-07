package com.softsquared.instagramlagame.src.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.instagramlagame.databinding.GridItemBinding

class SearchRVAdapter(private val list: ArrayList<String>): RecyclerView.Adapter<SearchRVAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: GridItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: String){

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