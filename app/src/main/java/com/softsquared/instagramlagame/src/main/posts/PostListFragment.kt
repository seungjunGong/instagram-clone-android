package com.softsquared.instagramlagame.src.main.posts

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentPostListBinding
import com.softsquared.instagramlagame.src.main.home.HomeService
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.FeedResult
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.HomeFeedResponse

class PostListFragment : BaseFragment<FragmentPostListBinding>(FragmentPostListBinding::bind, R.layout.fragment_post_list),
        PostListFragmentInterface{

    val args : PostListFragmentArgs by navArgs()
    private var feedData = ArrayList<FeedResult>()
    private var isLoading = false
    private var pageNumber = 0
    private var postListRVD = PostListRVAdapter(feedData)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        PostListService(this).tryGetPostList(getPostId = args.getPostId, pageNumber)

        binding.postListBackBt.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.postListRcv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)


                // 리사이클러뷰 가장 마지막 index
                val lastPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!
                    .findLastCompletelyVisibleItemPosition()

                // 받아온 리사이클러 뷰 카운트
                val totalCount = recyclerView.adapter!!.itemCount
                if (!isLoading) {
                    if (lastPosition == totalCount - 1) {
                        moreItems()
                        isLoading = true
                    }
                }
            }
        })
    }

    fun moreItems() {
        pageNumber++
        binding.postListLoading.loadingProgressBar.visibility = View.VISIBLE
        PostListService(this).tryGetPostList(getPostId = args.getPostId, pageNumber)
    }

    override fun onGetPostListSuccess(response: HomeFeedResponse) {
        if(response.result.postList.isNotEmpty()) {
            if(isLoading) {
                binding.postListLoading.loadingProgressBar.visibility = View.GONE
                feedData.apply {
                    for(data in response.result.postList){
                        add(data)
                    }
                }
                binding.postListRcv.adapter!!.notifyItemInserted(feedData.size)
                isLoading = false
            } else{
                feedData = response.result.postList as ArrayList<FeedResult>
            }
            connectRVD()
        }
    }

    private fun connectRVD(){
        postListRVD = PostListRVAdapter(feedData)
        binding.postListRcv.adapter = postListRVD
    }

    override fun onGetPostListFailure(message: String) {
        binding.postListLoading.loadingProgressBar.visibility = View.GONE
    }


}