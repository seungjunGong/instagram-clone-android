package com.softsquared.instagramlagame.src.main.home.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.instagramlagame.databinding.*
import com.softsquared.instagramlagame.src.main.home.ui.StoryData

class StoryRVD (private val storyData: ArrayList<StoryData>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val HEADER = 0
    private val ITEM = 1

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) HEADER else ITEM

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER ->
                StoryHeaderViewHolder(
                    HomeStoryHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            ITEM ->
                StoryItemViewHolder(
                    HomeStoryItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            else -> {
                throw ClassCastException("Unknown viewType $viewType")
            }
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            // HEADER
            is StoryHeaderViewHolder -> {
            }
            // ITEM
            is StoryItemViewHolder -> {
                holder.bind(storyData[position-1])
            }
        }
    }

    override fun getItemCount(): Int {
        return storyData.size + 1
    }

    inner class StoryHeaderViewHolder(val binding: HomeStoryHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(){

        }
    }
    inner class StoryItemViewHolder(val binding: HomeStoryItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: StoryData){

        }
    }
}