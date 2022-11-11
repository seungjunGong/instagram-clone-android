package com.softsquared.instagramlagame.src.main.home.whole_recyclerview.story

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.databinding.*
import com.softsquared.instagramlagame.src.login.LoginActivity
import com.softsquared.instagramlagame.src.main.home.HomeFragmentDirections
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.story.models.ResultHomeStory
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.story.models.StoryData

class StoryRVD (private val storyData: ArrayList<ResultHomeStory>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
            is StoryHeaderViewHolder -> {}
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
        fun bind(data: ResultHomeStory){
            binding.storyUserNickName.text = data.nickname
            Glide.with(itemView)
                .load(data.profileUrl)
                .error("https://firebasestorage.googleapis.com/v0/b/instagramlagame.appspot.com/o/ic_profile.png?alt=media&token=0053a8f4-3cdf-44b7-8dbe-768ac4d4bba4")
                .into(binding.storyProfileOtherIv)
            if(data.storyDataList.size != data.visitCnt){
                binding.storyProfileOtherIv.background = ContextCompat.getDrawable(binding.storyProfileOtherIv.context, R.drawable.insta_border)
            } else {
                binding.storyProfileOtherIv.background = ContextCompat.getDrawable(binding.storyProfileOtherIv.context, R.drawable.insta_inactived_border)
            }

        }
    }
}