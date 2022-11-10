package com.softsquared.instagramlagame.src.main.posts

import android.animation.ValueAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.databinding.FeedItemBinding
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.FeedRVD
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.FeedVPD
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.FeedResult

class PostListRVAdapter(private val feedData: ArrayList<FeedResult>): RecyclerView.Adapter<PostListRVAdapter.ViewHolder>() {



    inner class ViewHolder(val binding: FeedItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FeedResult){
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: FeedItemBinding = FeedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(feedData[position])
        holder.binding.feedUserIdTv.setOnClickListener {
            fUserNickClickListener.onUserNickClick( feedData[position-1].nickname, feedData[position-1].userId)
        }
        holder.binding.feedGoCommentBt.setOnClickListener {
            fCommentClickListener.onCommentClick(feedData[position-1].postId)
        }
        holder.binding.feedCommentUserTv.setOnClickListener {
            fCommentClickListener.onCommentClick(feedData[position-1].postId)
        }
        holder.binding.feedAddCommentTv.setOnClickListener {
            fCommentClickListener.onCommentClick(feedData[position-1].postId)
        }
    }

    override fun getItemCount(): Int  = feedData.size


    // 클릭 리스너 구현
    private var isLike: Boolean = false
    private lateinit var fLikeClickListener: FeedRVD.FeedClickListener
    fun setFeedLikeClickListener(feedLikeClickListener: FeedRVD.FeedClickListener) {
        fLikeClickListener = feedLikeClickListener
    }

    interface FeedNickClickListener{
        fun onUserNickClick(userNick: String, userid: Int)
    }
    private lateinit var fUserNickClickListener: FeedNickClickListener
    fun setUserNickClickListener(userNickClickListener: FeedNickClickListener){
        fUserNickClickListener = userNickClickListener
    }

    interface FeedCommentClickListener{
        fun onCommentClick(postId: Int)
    }
    private lateinit var fCommentClickListener: FeedCommentClickListener
    fun setCommentClickListener(commentClickListener: FeedCommentClickListener){
        fCommentClickListener = commentClickListener
    }
}