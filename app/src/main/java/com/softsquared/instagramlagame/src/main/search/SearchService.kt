package com.softsquared.instagramlagame.src.main.search

import com.softsquared.instagramlagame.config.ApplicationClass
import com.softsquared.instagramlagame.src.main.search.models.SearchThumResponse
import retrofit2.Call
import retrofit2.Response

class SearchService (val searchFragmentInterface: SearchFragmentInterface) {

    fun tryGetSearchThumbNail(getPage: Int){
        val searchRetrofitInterface = ApplicationClass.sRetrofit.create(SearchRetrofitInterface::class.java)
        searchRetrofitInterface.getSearchThumbnail(getPage).enqueue(object : retrofit2.Callback<SearchThumResponse>{
            override fun onResponse(
                call: Call<SearchThumResponse>,
                response: Response<SearchThumResponse>,
            ) {
                searchFragmentInterface.onGetSearchThumbnailSuccess(response.body() as SearchThumResponse)
            }

            override fun onFailure(call: Call<SearchThumResponse>, t: Throwable) {
                searchFragmentInterface.onGetSearchThumbnailFailure(t.message ?: "통신 오류")
            }
        })
    }
}