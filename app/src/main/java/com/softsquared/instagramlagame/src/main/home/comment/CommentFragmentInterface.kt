package com.softsquared.instagramlagame.src.main.home.comment

import com.softsquared.instagramlagame.src.main.home.comment.models.CommentDetailResponse
import com.softsquared.instagramlagame.src.main.home.comment.models.CommentPageResponse
import com.softsquared.instagramlagame.src.main.home.comment.models.CommentPostResponse

interface CommentFragmentInterface {

    fun onGetCommentDetailSuccess(response : CommentDetailResponse)

    fun onGetCommentDetailFailure(message: String)

    fun onGetCommentPageSuccess(response: CommentPageResponse)

    fun onGetCommentPageFailure(message: String)

    fun onPostCommentsSuccess(response: CommentPostResponse)

    fun onPostCommentFailure(message: String)

}