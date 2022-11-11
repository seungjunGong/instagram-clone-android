package com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.instagramlagame.databinding.FeedVpItemBinding
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.FeedImgUrl

class FeedVPD(private val feedImageList : List<FeedImgUrl>) : RecyclerView.Adapter<FeedVPD.PagerViewHolder>() {

    // 뷰페이저 인디케이터 전달하기 위한 인터페이스
    interface FeedChangeListener{
        fun onFeedChange(position: Int)
    }

    private lateinit var fChangeListener: FeedChangeListener
    fun setFeedChangeListener(feedChangeListener: FeedChangeListener){
        fChangeListener = feedChangeListener
    }

    // item xml 을 받아와 홀더를 만들어준다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding: FeedVpItemBinding = FeedVpItemBinding .inflate(LayoutInflater.from(parent.context), parent, false)

        return PagerViewHolder(binding)
    }

    // 바인딩한 데이터를 홀더에 넣어준다.
    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(feedImageList[position])
    }

    override fun getItemCount(): Int = feedImageList.size

    inner class PagerViewHolder(private val binding: FeedVpItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(image: FeedImgUrl) {
            Glide.with(itemView.context).load(image.imgUrl).error("https://firebasestorage.googleapis.com/v0/b/instagramlagame.appspot.com/o/feed_image_sample.PNG?alt=media&token=d83e31ab-c0e6-4546-8820-fab7e1a7e552").into(binding.feedIv)
        }
    }
}