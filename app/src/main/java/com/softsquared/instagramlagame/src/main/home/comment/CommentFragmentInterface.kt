package com.softsquared.instagramlagame.src.main.home.comment

import com.softsquared.instagramlagame.src.main.home.comment.models.CommentDetailResponse
import com.softsquared.instagramlagame.src.main.home.comment.models.CommentPageResponse

interface CommentFragmentInterface {

    fun onGetCommentDetailSuccess(response : CommentDetailResponse)

    fun onGetCommentDetailFailure(message: String)

    fun onGetCommentPageSuccess(response: CommentPageResponse)

    fun onGetCommentPageFailure(message: String)

}