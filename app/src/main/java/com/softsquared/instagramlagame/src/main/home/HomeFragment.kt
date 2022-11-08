package com.softsquared.instagramlagame.src.main.home


import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.ApplicationClass
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentHomeBinding
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.FeedRVD
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.FeedLikeResponse
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.FeedResult
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.HomeFeedResponse
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.story.StoryData
import com.softsquared.instagramlagame.src.main.profile.ProFileFragmentInterface
import com.softsquared.instagramlagame.src.main.profile.models.ProFileMyDataResponse


class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home),
    HomeFragmentInterface, ProFileFragmentInterface {

    private var feedData = ArrayList<FeedResult>()
    private var  storyData = ArrayList<StoryData>()
    private var isLoading = false
    private var pageNumber = 0
    private val homeRVD  = FeedRVD(feedData, storyData)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 홈화면 정보 받기
        binding.profileLoading.loadingMainProgressBar.visibility = View.VISIBLE
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

        // feed like 처리
        homeRVD.setFeedLikeClickListener(object : FeedRVD.FeedClickListener{
            override fun onLikeClick(postId: Int, liked: Boolean) {
                if(liked){
                    HomeService(this@HomeFragment).tryPatchFeedLike(postId)

                } else{
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
        if (response.code == 1000) {
            binding.profileLoading.loadingMainProgressBar.visibility = View.GONE

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

                storyData.apply {
                    add(StoryData(1))
                    add(StoryData(1))
                    add(StoryData(1))
                    add(StoryData(1))
                    add(StoryData(1))
                    add(StoryData(1))
                    add(StoryData(1))

                    binding.homeRcv.adapter = homeRVD
                }
            }

        } else {
            HomeService(this).tryGetFeed(pageNumber)
        }


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

    override fun onGetProFileMyDataSuccess(response: ProFileMyDataResponse) {

        // 유저 id 저장
        val editor : SharedPreferences.Editor = ApplicationClass.sSharedPreferences.edit()
        editor.putInt(ApplicationClass.USER_ID, response.resultProFileMyData.userId)
        editor.apply()

        // 유저 아이디 스토리 데이터 넣어 줄 떄 사용
        response.resultProFileMyData.profileUrl
    }

    override fun onGetProFileMyDataFailure(message: String) {
        TODO("Not yet implemented")
    }
}