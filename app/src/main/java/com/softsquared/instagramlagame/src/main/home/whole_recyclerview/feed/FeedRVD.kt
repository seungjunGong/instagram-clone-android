package com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.databinding.EmptyLoadingBinding

import com.softsquared.instagramlagame.databinding.FeedItemBinding
import com.softsquared.instagramlagame.databinding.HomeStoryBinding
import com.softsquared.instagramlagame.src.main.home.HomeFragmentDirections
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.FeedResult
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.story.StoryRVD
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.story.models.ResultHomeStory
import com.softsquared.instagramlagame.src.signup.birthday.BirthDayFragmentDirections

class FeedRVD(
    private val feedData: ArrayList<FeedResult>,
    private val storyData: ArrayList<ResultHomeStory>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val HEADER = 0 // 헤더 뷰
    private val ITEM = 1 // 리사이클러 피드 아이템 뷰
    private val EMPTY = 2 // 데이터가 없을 때 뜨는 뷰

    // 피드 클릭 리스너
    interface FeedClickListener {
        fun onLikeClick(postId: Int, liked: Boolean)
    }

    private var isLike: Boolean = false
    private lateinit var fLikeClickListener: FeedClickListener
    fun setFeedLikeClickListener(feedLikeClickListener: FeedClickListener) {
        fLikeClickListener = feedLikeClickListener
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> {
                HEADER
            }
            feedData.size + 1 -> {
                EMPTY
            }
            else -> {
                ITEM
            }
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
                    EmptyLoadingBinding.inflate(
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
        return if (feedData.size == 0) 1 else feedData.size + 2
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
                holder.bind(feedData[position - 1])
                holder.binding.feedUserIdTv.setOnClickListener {
                    fUserNickClickListener.onUserNickClick( feedData[position-1].nickname, feedData[position-1].userId)
                }
            }
            is EmptyViewHolder -> {}
        }
    }

    // 헤더 부분에 해당하는 뷰객체 가지는 뷰홀더 스토리를 띄워줌
    inner class HeaderViewHolder(val binding: HomeStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
        }
    }


    // 피드 항목에 해당하는 뷰객체 가지는 뷰홀더
    inner class ItemViewHolder(val binding: FeedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FeedResult) {
            var likeCount = data.likeCount
            // 데이터 연결
            Glide.with(binding.feedProfileIv.context)
                .load(data.userImg)
                .error("https://firebasestorage.googleapis.com/v0/b/instagramlagame.appspot.com/o/ic_basic_profile.png?alt=media&token=6632dbfe-f55f-4692-b5b5-f3ee0cbb04cb")
                .into(binding.feedProfileIv)

            with(binding) {
                feedUserIdTv.text = data.nickname
                feedLikeCountTv.text = data.likeCount.toString() + "개"
                feedCommentUserIdTv.text = data.nickname
                feedCommentUserTv.text = data.content
                feedCreateTimeTv.text = data.time
                if (data.commentCount == 0) {
                    feedGoCommentTv.visibility = View.GONE
                } else {
                    feedGoCommentTv.text = "댓글 ${data.commentCount}개 모두 보기"

                }
                if (data.storyExist != "Activated") {
                    binding.feedProfileIv.background =
                        ContextCompat.getDrawable(binding.feedProfileIv.context, R.color.white)
                }

                if (data.myPostLike == 1) {
                    // Custom animation speed or duration.
                    val animator = ValueAnimator.ofFloat(0F, 0.5F).setDuration(500)
                    animator.addUpdateListener { animation: ValueAnimator ->
                        binding.feedLikeBt.progress = animation.animatedValue as Float
                    }
                    animator.start()
                    isLike = true
                } else {
                    isLike = false
                }
            }

            // 좋아요 처리
            binding.feedLikeBt.setOnClickListener {
                Log.d("HeartCheck", "${data.myPostLike}")
                fLikeClickListener.onLikeClick(postId = data.postId, isLike)
                // 좋아요 상태 x
                if (!isLike) {
                    // Custom animation speed or duration.
                    val animator = ValueAnimator.ofFloat(0F, 0.5F).setDuration(500)
                    animator.addUpdateListener { animation: ValueAnimator ->
                        binding.feedLikeBt.progress = animation.animatedValue as Float
                    }
                    animator.start()
                    binding.feedLikeCountTv.text = (likeCount + 1).toString() + "개"
                    likeCount++
                    isLike = true
                } else {
                    // Custom animation speed or duration.
                    val animator = ValueAnimator.ofFloat(0.5F, 1F).setDuration(500)
                    animator.addUpdateListener { animation: ValueAnimator ->
                        binding.feedLikeBt.progress = animation.animatedValue as Float
                    }
                    animator.start()
                    binding.feedLikeCountTv.text = (likeCount - 1).toString() + "개"
                    likeCount--
                    isLike = false
                }
            }


            // 피드 뷰페이저 연결

            val feedVPD = FeedVPD(data.imgUrlList)
            binding.feedVp.adapter = feedVPD
            binding.feedVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            // indicator 설정
            val itemSize = data.imgUrlList.size
            binding.feedIndicator.noOfPages = itemSize
            binding.feedVp.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val itemNum = binding.feedVp.currentItem
                    // 인디케이터
                    binding.feedPageIndicator.visibility = View.VISIBLE

                    binding.feedIndicator.visibility = View.VISIBLE

                    binding.feedIndicator.onPageChange(itemNum)

                    // 페이지 번호
                    binding.feedPageIndicator.text = "${itemNum + 1}/$itemSize"
                    if (itemSize == 1) {
                        binding.feedIndicator.visibility = View.GONE
                        binding.feedPageIndicator.visibility = View.GONE
                    }
                }

            })


        }
    }

    interface FeedUserNickClickListener{
        fun onUserNickClick(userNick: String, userid: Int)
    }
    private lateinit var fUserNickClickListener: FeedUserNickClickListener
    fun setUserNickClickListener(userNickClickListener: FeedUserNickClickListener){
        fUserNickClickListener = userNickClickListener
    }

    // 데이터가 없을 때 보여줄 부분에 해당하는 뷰객체 가지는 뷰홀더
    inner class EmptyViewHolder(val binding: EmptyLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }


}