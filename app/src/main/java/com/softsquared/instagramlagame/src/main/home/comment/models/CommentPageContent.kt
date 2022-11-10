package com.softsquared.instagramlagame.src.main.home.comment.models


import com.google.gson.annotations.SerializedName

data class CommentPageContent(
    @SerializedName("commentId")
    val commentId: Int,
    @SerializedName("commentNum")
    val commentNum: Int,
    @SerializedName("content")
    val content: String,
    @SerializedName("likeCount")
    val likeCount: Int,
    @SerializedName("myLike")
    val myLike: Int,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("parentId")
    val parentId: Int,
    @SerializedName("postId")
    val postId: Int,
    @SerializedName("storyExist")
    val storyExist: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("userImg")
    val userImg: String
)