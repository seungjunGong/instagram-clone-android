package com.softsquared.instagramlagame.src.main.home


import android.media.Image
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentHomeBinding
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.FeedRVD
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.FeedLikeResponse
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.FeedResult
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.feed.models.HomeFeedResponse
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.story.models.HomeStoryResponse
import com.softsquared.instagramlagame.src.main.home.whole_recyclerview.story.models.ResultHomeStory
import com.softsquared.instagramlagame.src.main.profile.ProFileFragmentInterface
import com.softsquared.instagramlagame.src.main.profile.ProFileService
import com.softsquared.instagramlagame.src.main.profile.models.ProFileMyDataResponse


class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home),
    HomeFragmentInterface, ProFileFragmentInterface {

    private var feedData = ArrayList<FeedResult>()
    private var storyData = ArrayList<ResultHomeStory>()
    private var isLoading = false
    private var pageNumber = 0
    private var feedLoading = false
    private var storyLoading = false
    private var homeRVD = FeedRVD(feedData,storyData)
    private var userNick : String = ""
    private var userUrl : String = ""
    private var userID : Int = 0



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 홈화면 정보 받기
        binding.mainLoading.loadingMainProgressBar.visibility = View.VISIBLE
        HomeService(this).tryGetHomeStory()
        HomeService(this).tryGetFeed(pageNumber)
        ProFileService(this).tryGetProFileMyData()
        binding.homeRcv.layoutManager = LinearLayoutManager(requireContext())


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

    }



    fun moreItems() {
        pageNumber++
        HomeService(this).tryGetFeed(pageNumber)
    }


    override fun onGetFeedSuccess(response: HomeFeedResponse) {

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

                feedLoading = true
                isLoading = false
            } else {
                feedData = response.result.postList as ArrayList<FeedResult>
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
            homeRVD = FeedRVD(feedData, storyData)
            binding.homeRcv.adapter = homeRVD

            homeRVD.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.ALLOW


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

            // 유저 닉네임 클릭 리스너
            homeRVD.setUserNickClickListener(object : FeedRVD.FeedNickClickListener {
                override fun onUserNickClick(userNick: String, userid: Int) {
                    // 데이터 전달
                    val action = HomeFragmentDirections.actionHomeFragmentToOthersProFileFragment(
                        userNickName = userNick,
                        userId = userid)
                    Navigation.findNavController(requireView()).navigate(action)
                }
            })

            // 코멘트 클릭 리스너
            homeRVD.setCommentClickListener(object : FeedRVD.FeedCommentClickListener{
                override fun onCommentClick(postId: Int) {
                    // 데이터 전달
                    Log.d("HomeTest","$postId, $userNick, $userUrl")
                    val action = HomeFragmentDirections.actionHomeFragmentToCommentFragment(postId = postId, getNick =  userNick, getUserImage= userUrl)
                    Navigation.findNavController(requireView()).navigate(action)
                    hideBttnav()
                }
            })

            Log.d("HomeFragment", "$homeRVD")
            binding.mainLoading.loadingMainProgressBar.visibility = View.GONE
        }
    }


    override fun onGetFeedFailure(message: String) {
        binding.mainLoading.loadingMainProgressBar.visibility = View.GONE
        Log.d("HomeFragment", "ERRROR : $message")
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

        Log.d("HomeFragment", "$response")
        with(response.resultHomeStory) {
            storyData.apply {
                for (result in this@with) {
                        add(result)
                }

            }
            storyData = response.resultHomeStory as ArrayList<ResultHomeStory> /* = java.util.ArrayList<com.softsquared.instagramlagame.src.main.home.whole_recyclerview.story.models.ResultHomeStory> */

            storyLoading = true
            checkLoading()

        }
        Log.d("storyData", "$storyData")

    }

    override fun onGetHomeStoryFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetProFileMyDataSuccess(response: ProFileMyDataResponse) {
        Log.d("HomeTest","$response")
        userNick = response.resultProFileMyData.nickname
        userID = response.resultProFileMyData.userId
        userUrl = response.resultProFileMyData.profileUrl

    }

    override fun onGetProFileMyDataFailure(message: String) {
        TODO("Not yet implemented")
    }


}