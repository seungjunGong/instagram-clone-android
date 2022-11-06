package com.softsquared.instagramlagame.src.main.home

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentHomeBinding
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.FeedRVD
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.FeedResult
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.HomeFeedResponse
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.story.StoryData
import kotlinx.coroutines.*
import java.lang.Runnable

class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home),
    HomeFragmentInterface {

    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
    private var feedData = ArrayList<FeedResult?>()
    private var isLoading = false
    private var pageNumber = 0
    private var pages: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        HomeService(this).tryGetFeed(pageNumber)

        binding.homeRcv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!isLoading) {
                    if ((recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition() == feedData.size - 1) {
                        Log.e("true", "True")
                        moreItems()
                        isLoading = true
                    }
                }
            }
        })
    }

    fun moreItems() {

        val runnable = Runnable{
            feedData.add(null)
            binding.homeRcv.adapter!!.notifyItemInserted(feedData.size - 1)
        }
        binding.homeRcv.post(runnable)

        HomeService(this).tryGetFeed(pageNumber)
    }


    override fun onGetFeedSuccess(response: HomeFeedResponse) {
        if (response.code == 1000) {


            if (isLoading) {
                feedData.removeAt(feedData.size - 1)
                val scrollPosition = feedData.size
                binding.homeRcv.adapter!!.notifyItemRemoved(scrollPosition)

                feedData.apply {
                    for (feedData in response.result.postList) {
                        add(feedData)
                    }
                }

                (binding.homeRcv.adapter as FeedRVD).updateItem(feedData)

                isLoading = false
            } else{
                feedData.apply {
                    for (feedData in response.result.postList) {
                        add(feedData)
                    }
                }
                var storyData = ArrayList<StoryData>()
                storyData.apply {
                    add(StoryData(1))
                    add(StoryData(1))
                    add(StoryData(1))
                    add(StoryData(1))
                    add(StoryData(1))
                    add(StoryData(1))
                    add(StoryData(1))
                    val homeRVD = FeedRVD(feedData, storyData, pages)
                    binding.homeRcv.adapter = homeRVD
            }
        }


        }

    }


    override fun onGetFeedFailure(message: String) {
        TODO("Not yet implemented")
    }
}