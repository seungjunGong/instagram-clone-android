package com.softsquared.instagramlagame.src.main.home.comment.models


import com.google.gson.annotations.SerializedName
import com.softsquared.instagramlagame.config.BaseResponse

data class CommentDetailResponse(
    @SerializedName("result")
    val resultCommentDetail: ResultCommentDetail
) : BaseResponse()