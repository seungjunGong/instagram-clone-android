package com.softsquared.instagramlagame.src.main.search

import android.os.Bundle
import android.system.Os.bind
import android.util.Log
import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentSearchBinding
import com.softsquared.instagramlagame.src.main.home.HomeService
import com.softsquared.instagramlagame.src.main.search.models.ResultSearchThum
import com.softsquared.instagramlagame.src.main.search.models.SearchThumResponse
import com.softsquared.instagramlagame.util.GridSpacingItemDecoration
import okhttp3.internal.addHeaderLenient


class SearchFragment :
    BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search),
    SearchFragmentInterface {

    private var searchData = ArrayList<ResultSearchThum>()
    private var page = 0
    private var isLoading = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        SearchService(this).tryGetSearchThumbNail(page)

        binding.searchBt.setOnClickListener{
            val action = SearchFragmentDirections.actionSearchFragmentToAutoSearchFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }

        binding.searchRcv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)


                // 리사이클러뷰 가장 마지막 index
                val lastPosition = (recyclerView.layoutManager as GridLayoutManager?)!!
                    .findLastCompletelyVisibleItemPosition()

                // 받아온 리사이클러 뷰 카운트
                val totalCount = recyclerView.adapter!!.itemCount
                Log.d("SearchTest", "$lastPosition, $totalCount")

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
        page++
        binding.searchLoadingProgressBar.signUpPhoneProgressBar.visibility = View.VISIBLE
        Log.d("SearchTest", "추가 함수 실행")
        SearchService(this).tryGetSearchThumbNail(page)
    }

    override fun onGetSearchThumbnailSuccess(response: SearchThumResponse) {

        if (response.resultSearchThum.isNotEmpty()) {
            if (isLoading) {
                binding.searchLoadingProgressBar.signUpPhoneProgressBar.visibility = View.GONE
                searchData.apply {
                    for (data in response.resultSearchThum) {
                        add(data)
                    }
                }

                binding.searchRcv.adapter!!.notifyItemInserted(searchData.size)
                isLoading = false
            } else {
                searchData = response.resultSearchThum
                val spanCount = 3 // 3 columns

                val spacing = 10 // 50px

                val includeEdge = false
                binding.searchRcv.addItemDecoration(GridSpacingItemDecoration(spanCount,
                    spacing,
                    includeEdge))
                val layoutManager = GridLayoutManager(requireContext(), 3)
//        layoutManager.spanSizeLookup = object :GridLayoutManager.SpanSizeLookup(){
//            override fun getSpanSize(position: Int): Int {
//                return when(position){
//                    0 -> 1
//                    list.size -> 1
//                    else -> 3
//                }
//            }
//        }
                binding.searchRcv.setHasFixedSize(true)
                binding.searchRcv.layoutManager = layoutManager
                binding.searchRcv.adapter = SearchRVAdapter(searchData)

            }

        } else {
            page = 0
            SearchService(this).tryGetSearchThumbNail(page)
        }


    }

    override fun onGetSearchThumbnailFailure(message: String) {
        TODO("Not yet implemented")
    }


}
