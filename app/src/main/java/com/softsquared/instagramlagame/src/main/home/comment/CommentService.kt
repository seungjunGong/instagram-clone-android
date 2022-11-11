package com.softsquared.instagramlagame.src.main.home.comment

import com.softsquared.instagramlagame.config.ApplicationClass
import com.softsquared.instagramlagame.src.main.home.HomeRetrofitInterface
import com.softsquared.instagramlagame.src.main.home.comment.models.CommentDetailResponse
import com.softsquared.instagramlagame.src.main.home.comment.models.CommentPageResponse
import com.softsquared.instagramlagame.src.main.home.comment.models.CommentPostResponse
import com.softsquared.instagramlagame.src.main.home.comment.models.RequestPostComment
import retrofit2.Call
import retrofit2.Response

class CommentService (val commentFragmentInterface: CommentFragmentInterface) {

    fun tryGetCommentDetail(getPostId : Int){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getCommentDetail(getPostId).enqueue(object  : retrofit2.Callback<CommentDetailResponse>{
            override fun onResponse(
                call: Call<CommentDetailResponse>,
                response: Response<CommentDetailResponse>,
            ) {
                commentFragmentInterface.onGetCommentDetailSuccess(response.body() as CommentDetailResponse)
            }

            override fun onFailure(call: Call<CommentDetailResponse>, t: Throwable) {
                commentFragmentInterface.onGetCommentDetailFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryGetCommentPage(getPostId: Int, getPage: Int){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.patchCommentPage(getPostId, getPage).enqueue(object : retrofit2.Callback<CommentPageResponse>{
            override fun onResponse(
                call: Call<CommentPageResponse>,
                response: Response<CommentPageResponse>,
            ) {
                commentFragmentInterface.onGetCommentPageSuccess(response.body() as CommentPageResponse)
            }

            override fun onFailure(call: Call<CommentPageResponse>, t: Throwable) {
                commentFragmentInterface.onGetCommentPageFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryPostComments(postComments : RequestPostComment){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.postComments(postComments).enqueue(object : retrofit2.Callback<CommentPostResponse>{
            override fun onResponse(
                call: Call<CommentPostResponse>,
                response: Response<CommentPostResponse>,
            ) {
                commentFragmentInterface.onPostCommentsSuccess(response.body() as CommentPostResponse)
            }

            override fun onFailure(call: Call<CommentPostResponse>, t: Throwable) {
                commentFragmentInterface.onPostCommentFailure(t.message ?: "통신 오류")
            }

        })
    }
}