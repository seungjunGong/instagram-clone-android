package com.softsquared.instagramlagame.src.main.home.comment

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.text.toSpannable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.instagramlagame.databinding.CommentItemBinding
import com.softsquared.instagramlagame.src.main.home.comment.models.CommentPageContent

class CommentRVAdapter(private val commentData: List<CommentPageContent>): RecyclerView.Adapter<CommentRVAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: CommentItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CommentPageContent){
            if(data.storyExist == "Activated"){
                Glide.with(binding.commentProfileIv.context).load(data.userImg).error("https://firebasestorage.googleapis.com/v0/b/instagramlagame.appspot.com/o/ic_basic_profile.png?alt=media&token=6632dbfe-f55f-4692-b5b5-f3ee0cbb04cb").into(binding.commentProfileIv)

            } else{
                binding.commentProfileIv.setBackgroundColor(Color.parseColor("#FFFFFF"))
                Glide.with(binding.commentProfileIv.context).load(data.userImg).error("https://firebasestorage.googleapis.com/v0/b/instagramlagame.appspot.com/o/ic_profile.png?alt=media&token=0053a8f4-3cdf-44b7-8dbe-768ac4d4bba4").into(binding.commentProfileIv)
            }
            val span : Spannable = ("${data.nickname} ${data.content}").toSpannable()
            span.setSpan(StyleSpan(Typeface.BOLD), 0, data.nickname.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            binding.commentContentTv.text = span
            binding.commentTime.text = data.time
            binding.likeCountTv.text = "좋아요 ${data.likeCount}개"

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: CommentItemBinding = CommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(commentData[position])
    }

    override fun getItemCount(): Int = commentData.size
}