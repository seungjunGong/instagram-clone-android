package com.softsquared.instagramlagame.src.main.search

import android.os.Bundle
import android.system.Os.bind
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.config.BaseFragment
import com.softsquared.instagramlagame.databinding.FragmentSearchBinding
import com.softsquared.instagramlagame.util.GridSpacingItemDecoration


class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var list = arrayListOf(
            "1","1",
            "1","1",
            "1","1",
            "1","1",
            "1","1",
            "1","1",
            "1","1",
            "1","1",
            "1","1",
            "1","1",
            "1","1",
            "1","1",
            "1","1",
            "1","1",
            "1","1"
            )
        val spanCount = 3 // 3 columns

        val spacing = 10 // 50px

        val includeEdge = false
        binding.searchRcv.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
        val layoutManager = GridLayoutManager(requireContext(), 3)
//        layoutManager.spanSizeLookup = object :GridLayoutManager.SpanSizeLookup(){
//            override fun getSpanSize(position: Int): Int {
//                return when(position){
//                    0 -> 2
//                    else -> 1
//                }
//            }
//        }
        binding.searchRcv.setHasFixedSize(true)
        binding.searchRcv.layoutManager = layoutManager
        binding.searchRcv.adapter = SearchRVAdapter(list)



    }




}
