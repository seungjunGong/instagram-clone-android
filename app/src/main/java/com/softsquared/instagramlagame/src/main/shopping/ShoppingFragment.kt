package com.softsquared.instagramlagame.src.main.shopping

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentShoppingBinding
import com.softsquared.instagramlagame.src.main.search.SearchRVAdapter
import com.softsquared.instagramlagame.src.main.search.SearchService
import com.softsquared.instagramlagame.src.main.search.models.ResultSearchThum
import com.softsquared.instagramlagame.src.main.shopping.models.ResultShoppingList
import com.softsquared.instagramlagame.src.main.shopping.models.ShoppingListResponse
import com.softsquared.instagramlagame.util.GridSpacingItemDecoration

class ShoppingFragment : BaseFragment<FragmentShoppingBinding>(FragmentShoppingBinding::bind, R.layout.fragment_shopping) ,
        ShoppingFragmentInterface{

    private var shoppingData = ArrayList<ResultShoppingList>()
    private var page = 0
    private var isLoading = false
    private var shoppingRVD = ShoppingRVAdater(shoppingData)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        ShoppingService(this).tryGetShoppingList(page)


        binding.shoppingRcv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)


                // 리사이클러뷰 가장 마지막 index
                val lastPosition = (recyclerView.layoutManager as GridLayoutManager?)!!
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
        page++
        binding.shoppingLoadingBar.loadingProgressBar.visibility = View.VISIBLE
        ShoppingService(this).tryGetShoppingList(page)
    }

    override fun onGetShoppingListSuccess(response: ShoppingListResponse) {
        if (response.result.isNotEmpty()) {
            if (isLoading) {
                binding.shoppingLoadingBar.loadingProgressBar.visibility = View.GONE
                shoppingData.apply {
                    for (data in response.result) {
                        add(data)
                    }
                }

                binding.shoppingRcv.adapter!!.notifyItemInserted(shoppingData.size)
                isLoading = false
            } else {
                shoppingData = response.result

                val spanCount = 2 // 3 columns

                val spacing = 20 // 50px

                val includeEdge = false
                binding.shoppingRcv.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
                val layoutManager = GridLayoutManager(requireContext(), 2)


                binding.shoppingRcv.setHasFixedSize(true)
                binding.shoppingRcv.layoutManager = layoutManager
                shoppingRVD = ShoppingRVAdater(shoppingData)
                binding.shoppingRcv.adapter = shoppingRVD
            }
        } else {
            page = 0
            ShoppingService(this).tryGetShoppingList(page)
        }
    }

    override fun onGetShoppingListFailure(message: String) {
        TODO("Not yet implemented")
    }

}