package com.softsquared.instagramlagame.src.main.home


import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.ApplicationClass
import com.softsquared.instagramlagame.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentHomeBinding
import com.softsquared.instagramlagame.src.main.MainActivity
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.FeedRVD
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.FeedLikeResponse
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.FeedResult
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.HomeFeedResponse
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.story.models.HomeStoryResponse
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.story.models.ResultHomeStory
import com.softsquared.instagramlagame.src.main.profile.ProFileFragmentInterface
import com.softsquared.instagramlagame.src.main.profile.models.ProFileMyDataResponse


class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home),
    HomeFragmentInterface {

    private var feedData = ArrayList<FeedResult>()
    private var storyData = ArrayList<ResultHomeStory>()
    private var isLoading = false
    private var pageNumber = 0
    private val homeRVD = FeedRVD(feedData, storyData)
    private var feedLoading = false
    private var storyLoading = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 홈화면 정보 받기
        binding.profileLoading.loadingMainProgressBar.visibility = View.VISIBLE
        HomeService(this).tryGetHomeStory()
        HomeService(this).tryGetFeed(pageNumber)

        binding.homeRcv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)


                // 리사이클러뷰 가장 마지막 index
                val lastPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!
                    .findLastCompletelyVisibleItemPosition()

                // 받아온 리사이클러 뷰 카운트
                val totalCount = recyclerView.adapter!!.itemCount
                if (!isLoading) {
                    if (lastPosition == totalCount - 1) {
                        Log.e("true", "True")
                        moreItems()
                        isLoading = true
                    }
                }
            }
        })

        // 포스트 프레그먼트 이동
        binding.homeGoUploadIv.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToPostPostingFragment()
            Navigation.findNavController(requireView()).navigate(action)
            applyBlackColors()
        }
        // feed like 처리
        homeRVD.setFeedLikeClickListener(object : FeedRVD.FeedClickListener {
            override fun onLikeClick(postId: Int, liked: Boolean) {
                if (liked) {
                    HomeService(this@HomeFragment).tryPatchFeedLike(postId)

                } else {
                    HomeService(this@HomeFragment).tryPostFeedLike(postId)

                }
            }

        })
    }

    fun moreItems() {
        pageNumber++
        HomeService(this).tryGetFeed(pageNumber)
    }


    override fun onGetFeedSuccess(response: HomeFeedResponse) {

        Log.d("HomeFragment", "$response")
        if (response.result.postList.isNotEmpty()) {

            if (isLoading) {
                feedData.removeAt(feedData.size - 1)
                val scrollPosition = feedData.size
                binding.homeRcv.adapter!!.notifyItemRemoved(scrollPosition)

                feedData.apply {
                    for (feedData in response.result.postList) {
                        add(feedData)
                    }
                }
                binding.homeRcv.adapter!!.notifyItemInserted(feedData.size)


                isLoading = false
            } else {
                feedData.apply {
                    for (feedData in response.result.postList) {
                        add(feedData)
                    }
                }
                feedLoading = true
                checkLoading()
            }
        } else {
            pageNumber = 0
            HomeService(this).tryGetFeed(pageNumber)
        }
    }

    private fun checkLoading() {
        if (feedLoading && storyLoading) {
            binding.homeRcv.adapter = homeRVD
        }
        binding.profileLoading.loadingMainProgressBar.visibility = View.GONE

        // 유저 닉네임 클릭 리스너
        homeRVD.setUserNickClickListener(object : FeedRVD.FeedUserNickClickListener {
            override fun onUserNickClick(userNick: String, userid: Int) {
                // 데이터 전달
                val action = HomeFragmentDirections.actionHomeFragmentToOthersProFileFragment(
                    userNickName = userNick,
                    userId = userid)
                Navigation.findNavController(requireView()).navigate(action)
            }
        })
    }


    override fun onGetFeedFailure(message: String) {
        binding.profileLoading.loadingMainProgressBar.visibility = View.GONE
    }

    override fun onPostFeedLikeSuccess(response: FeedLikeResponse) {
        Log.d("HomeFragmentLog", "Post 성공 ${response.isSuccess}")
    }

    override fun onPostFeedLikeFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPatchFeedLikeSuccess(response: FeedLikeResponse) {
        Log.d("HomeFragmentLog", "Patch 성공 ${response.isSuccess}")
    }

    override fun onPatchFeedLikeFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetHomeStorySuccess(response: HomeStoryResponse) {

        Log.d("storyData", "$response")
        with(response.resultHomeStory) {
            storyData.apply {
                for (result in this@with) {
                    if (result.visitCnt < result.storyDataList.size) {
                        add(result)
                    }
                }
                for (result in this@with) {
                    if (result.visitCnt > result.storyDataList.size) {
                        add(result)
                    }
                }
            }

            storyLoading = true
            checkLoading()

        }
        Log.d("storyData", "$storyData")

    }

    override fun onGetHomeStoryFailure(message: String) {
        TODO("Not yet implemented")
    }


}