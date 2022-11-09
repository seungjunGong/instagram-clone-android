package com.softsquared.instagramlagame.src.main.search

import com.softsquared.instagramlagame.src.main.search.auto_search.models.AutoSearchResponse
import com.softsquared.instagramlagame.src.main.search.models.SearchThumResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchRetrofitInterface {

    @GET("/searchs/getList")
    fun getSearchThumbnail(@Query("cursor") page: Int) : Call<SearchThumResponse>

    @GET("/searchs/autoSearch")
    fun getAutoSearch(@Query("searchKey") searchName: String, @Query("cursor") page: Int) : Call<AutoSearchResponse>
}