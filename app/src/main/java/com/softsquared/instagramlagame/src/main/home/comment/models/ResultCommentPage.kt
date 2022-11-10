package com.softsquared.instagramlagame.src.main.home.comment.models


import com.google.gson.annotations.SerializedName

data class ResultCommentPage(
    @SerializedName("commentList")
    val commentPageContentList: List<CommentPageContent>,
    @SerializedName("numOfComments")
    val numOfComments: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("postId")
    val postId: Int
)