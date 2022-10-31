package com.softsquared.instagramlagame.src.main.home.whole_recyclerview

import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.softsquared.instagramlagame.databinding.FeedEmptyBinding
import com.softsquared.instagramlagame.databinding.FeedItemBinding
import com.softsquared.instagramlagame.databinding.HomeStoryBinding
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.story.StoryData
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.story.StoryRVD

class FeedRVD (private val feedData: ArrayList<FeedData>, private val storyData: ArrayList<StoryData>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val HEADER = 0 // 헤더 뷰
    private val ITEM = 1 // 리사이클러 피드 아이템 뷰
    private val EMPTY = 2 // 데이터가 없을 때 뜨는 뷰

    override fun getItemViewType(position: Int): Int {
        return if (feedData.size != 0) {
            if (position == 0) HEADER else ITEM
        } else {
            EMPTY
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER ->
                HeaderViewHolder(
                    HomeStoryBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            ITEM ->
                ItemViewHolder(
                    FeedItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            EMPTY -> // EMPTY
                EmptyViewHolder(
                    FeedEmptyBinding.inflate(
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

    override fun getItemCount(): Int {
        return if (feedData.size == 0) 1 else feedData.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            // HEADER
            is HeaderViewHolder -> {
                val storyRVD = StoryRVD(storyData)
                holder.binding.storyRcv.adapter = storyRVD
                Log.d("storyData", "실행 $storyData")

            }
            // ITEM
            is ItemViewHolder -> {
                holder.bind(feedData[position-1])
            }
            is EmptyViewHolder -> {}
        }
    }

    // 헤더 부분에 해당하는 뷰객체 가지는 뷰홀더 스토리를 띄워줌
    inner class HeaderViewHolder(val binding: HomeStoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(){
        }
    }


    // 피드 항목에 해당하는 뷰객체 가지는 뷰홀더
    inner class ItemViewHolder(private val binding: FeedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(data: FeedData){
                // 피드 뷰페이저 연결
                val result = getFeedImageList()
                val feedVPD = FeedVPD(result)
                binding.feedVp.adapter = feedVPD
                binding.feedVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                // indicator 설정
                val itemSize = result.size
                binding.feedIndicator.noOfPages = itemSize
                binding.feedVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        val itemNum = binding.feedVp.currentItem
                        // 인디케이터
                        binding.feedIndicator.onPageChange(itemNum)

                        // 페이지 번호
                        binding.feedPageIndicator.text = "${itemNum+1}/$itemSize"
                    }

                })
            }

        // 피드 이미지 받아오기
        fun getFeedImageList(): ArrayList<String> {
            return arrayListOf<String>(
                "https://firebasestorage.googleapis.com/v0/b/instagramlagame.appspot.com/o/feed_image_sample.PNG?alt=media&token=d83e31ab-c0e6-4546-8820-fab7e1a7e552",
                "https://firebasestorage.googleapis.com/v0/b/instagramlagame.appspot.com/o/feed_image_sample2.jfif?alt=media&token=62d1eab3-7a4a-4579-822d-735b0cbbc8e1",
                "https://firebasestorage.googleapis.com/v0/b/instagramlagame.appspot.com/o/feed_image_sample3.jfif?alt=media&token=a404f0a4-2589-47f7-b79c-c094a322ae6d",
                "https://firebasestorage.googleapis.com/v0/b/instagramlagame.appspot.com/o/feed_image_sample.PNG?alt=media&token=d83e31ab-c0e6-4546-8820-fab7e1a7e552",
                "https://firebasestorage.googleapis.com/v0/b/instagramlagame.appspot.com/o/feed_image_sample2.jfif?alt=media&token=62d1eab3-7a4a-4579-822d-735b0cbbc8e1",
                "https://firebasestorage.googleapis.com/v0/b/instagramlagame.appspot.com/o/feed_image_sample3.jfif?alt=media&token=a404f0a4-2589-47f7-b79c-c094a322ae6d",
                "https://firebasestorage.googleapis.com/v0/b/instagramlagame.appspot.com/o/feed_image_sample.PNG?alt=media&token=d83e31ab-c0e6-4546-8820-fab7e1a7e552",
            )
        }
    }
    // 데이터가 없을 때 보여줄 부분에 해당하는 뷰객체 가지는 뷰홀더
    inner class EmptyViewHolder(val binding: FeedEmptyBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(){

            }
        }











}