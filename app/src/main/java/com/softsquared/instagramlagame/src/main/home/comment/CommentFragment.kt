package com.softsquared.instagramlagame.src.main.home.comment

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.text.toSpannable
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentCommentBinding
import com.softsquared.instagramlagame.src.main.home.comment.models.*


class CommentFragment :
    BaseFragment<FragmentCommentBinding>(FragmentCommentBinding::bind, R.layout.fragment_comment),
    CommentFragmentInterface {

    private val args: CommentFragmentArgs by navArgs()

    private var commentData = ArrayList<CommentPageContent>()
    private var page = 0
    private var isLoading = false




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CommentService(this).tryGetCommentPage(args.postId, page)
        CommentService(this).tryGetCommentDetail(args.postId)

        Glide.with(binding.commentProfileIv.context).load(args.getUserImage).error("https://firebasestorage.googleapis.com/v0/b/instagramlagame.appspot.com/o/ic_profile.png?alt=media&token=0053a8f4-3cdf-44b7-8dbe-768ac4d4bba4").into(binding.commentProfileEtIv)
        binding.commentBackBt.setOnClickListener {
            Navigation.findNavController(requireView()).navigateUp()
            showBttnav()
        }

        binding.commentPostTv.setOnClickListener {
            val comment = binding.commentEt.text.toString()
            val postData = CommentPageContent(commentId = 0, postId = args.postId, userId = 0, userImg = args.getUserImage, nickname = args.getNick, content = comment, commentNum = 0, likeCount = 0, myLike = 0, parentId = 0, storyExist = "", time = "2초")
            commentData.add(commentData.size, postData)
            binding.commentRcv.adapter!!.notifyItemInserted(commentData.size)
            CommentService(this).tryPostComments(RequestPostComment(comment,args.postId))
            binding.commentEt.setText("")
        }

        binding.commentRcv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)


                // 리사이클러뷰 가장 마지막 index
                val lastPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!
                    .findLastCompletelyVisibleItemPosition()

                // 받아온 리사이클러 뷰 카운트
                val totalCount = recyclerView.adapter!!.itemCount
                Log.d("SearchTest", "$lastPosition, $totalCount")

            }
        })

        binding.commentEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                editTextCheck()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                editTextCheck()
            }
        })
    }

    fun editTextCheck(){
        if(binding.commentEt.text.toString().isNotEmpty()){
            binding.commentPostTv.setTextColor(Color.parseColor("#0195F7"))
            binding.commentPostTv.isClickable = true
        } else {
            binding.commentPostTv.setTextColor(Color.parseColor("#87CEFB"))
            binding.commentPostTv.isClickable = false
        }
    }

    fun moreItems() {
        page++
        binding.commentLoading.loadingProgressBar.visibility = View.VISIBLE
        CommentService(this).tryGetCommentPage(getPostId = args.postId, getPage = page)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // 백버튼 설정
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                view?.let {
                    Navigation.findNavController(requireView()).navigateUp()
                    showBttnav()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onGetCommentDetailSuccess(response: CommentDetailResponse) {
        with(response.resultCommentDetail) {
            val span: Spannable = ("$nickname $content").toSpannable()
            span.setSpan(StyleSpan(Typeface.BOLD),
                0,
                nickname.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            binding.commentUser.text = span
            binding.commentTime.text = time
            Glide.with(requireContext())
                .load("https://firebasestorage.googleapis.com/v0/b/instagramlagame.appspot.com/o/ic_profile.png?alt=media&token=0053a8f4-3cdf-44b7-8dbe-768ac4d4bba4")
                .into(binding.commentProfileIv)
        }
    }

    override fun onGetCommentDetailFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetCommentPageSuccess(response: CommentPageResponse) {


                val mLayoutManager = LinearLayoutManager(activity)
                mLayoutManager.reverseLayout = true
                mLayoutManager.stackFromEnd = true
                binding.commentRcv.layoutManager = mLayoutManager
                commentData =
                    response.resultCommentPage.commentPageContentList as ArrayList<CommentPageContent>
                binding.commentRcv.adapter = CommentRVAdapter(commentData)


    }


    override fun onGetCommentPageFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPostCommentsSuccess(response: CommentPostResponse) {
        Log.d("PostComment" ,"success $response")
    }

    override fun onPostCommentFailure(message: String) {
        TODO("Not yet implemented")
    }

}