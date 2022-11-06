package com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models


import com.google.gson.annotations.SerializedName

data class FeedResult(
    @SerializedName("commentCount")
    val commentCount: Int,
    @SerializedName("content")
    val content: String,
    @SerializedName("hashTagList")
    val hashTagList: List<String>,
    @SerializedName("imgUrlList")
    val imgUrlList: List<String>,
    @SerializedName("likeCount")
    val likeCount: Int,
    @SerializedName("myPostLike")
    val myPostLike: Int,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("postId")
    val postId: Int,
    @SerializedName("reels")
    val reels: String,
    @SerializedName("reelsMusic")
    val reelsMusic: String,
    @SerializedName("storyExist")
    val storyExist: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("userImg")
    val userImg: String,
    @SerializedName("userTagList")
    val userTagList: List<Int>
)