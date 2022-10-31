package com.softsquared.instagramlagame.src.main.home.whole_recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.instagramlagame.databinding.FeedVpItemBinding

class FeedVPD(private val feedImageList : ArrayList<String>) : RecyclerView.Adapter<FeedVPD.PagerViewHolder>() {

    // 뷰페이저 인디케이터 전달하기 위한 인터페이스
    interface FeedChangeListener{
        fun onFeedChange(position: Int)
    }

    private lateinit var fChangeListener: FeedChangeListener
    fun setFeedChangeListener(feedChangeListener: FeedChangeListener){
        fChangeListener = feedChangeListener
    }

    // item xml 을 받아와 홀더를 만들어준다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedVPD.PagerViewHolder {
        val binding: FeedVpItemBinding = FeedVpItemBinding .inflate(LayoutInflater.from(parent.context), parent, false)

        return PagerViewHolder(binding)
    }

    // 바인딩한 데이터를 홀더에 넣어준다.
    override fun onBindViewHolder(holder: FeedVPD.PagerViewHolder, position: Int) {
        holder.bind(feedImageList[position])
    }

    override fun getItemCount(): Int = feedImageList.size

    inner class PagerViewHolder(private val binding: FeedVpItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(image: String) {
            Glide.with(itemView.context).load(image).into(binding.feedIv)
        }
    }
}