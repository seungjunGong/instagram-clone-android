package com.softsquared.instagramlagame.src.main.others

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentOthersTabBinding
import com.softsquared.instagramlagame.src.main.profile.tab.ProFileTabRVAdapter
import com.softsquared.instagramlagame.src.main.user_thum.UserThumInterface
import com.softsquared.instagramlagame.src.main.user_thum.UserThumService
import com.softsquared.instagramlagame.src.main.user_thum.models.UserThumResponse
import com.softsquared.instagramlagame.util.GridSpacingItemDecoration

class OthersTabFragment(val type: String, val userId: Int) : BaseFragment<FragmentOthersTabBinding>(
    FragmentOthersTabBinding::bind,
    R.layout.fragment_others_tab),
    UserThumInterface {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (type) {
            "post" -> UserThumService(this).tryGetUserThumbNaill(userId, 0)
        }
    }


    override fun onGetUserThumbnailSuccess(response: UserThumResponse) {
        if (response.resultUserThum.thumbnailList.isEmpty()) {
            binding.othersTabNoItem.visibility = View.VISIBLE
        } else {
            val spanCount = 3 // 3 columns

            val spacing = 10 // 50px

            val includeEdge = false
            binding.othersTabRcv.addItemDecoration(GridSpacingItemDecoration(spanCount,
                spacing,
                includeEdge))
            val layoutManager = GridLayoutManager(requireContext(), 3)

            binding.othersTabRcv.setHasFixedSize(true)
            binding.othersTabRcv.layoutManager = layoutManager
            val proFileTabRVD = ProFileTabRVAdapter(response.resultUserThum.thumbnailList)
            binding.othersTabRcv.adapter = proFileTabRVD
            binding.othersTabRcv.visibility = View.VISIBLE

            proFileTabRVD.setFeedItemClickListener(object : ProFileTabRVAdapter.FeedItemClickListener{
                override fun onItemClick(postId: Int) {
                    val action =
                        OthersProFileFragmentDirections.actionOthersProFileFragmentToPostListFragment(postId)
                    Navigation.findNavController(requireView()).navigate(action)
                }
            })

        }
    }

    override fun onGetUserThumbnailFailure(message: String) {
        TODO("Not yet implemented")
    }
}