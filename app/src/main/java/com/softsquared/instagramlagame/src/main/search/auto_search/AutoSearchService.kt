package com.softsquared.instagramlagame.src.main.search.auto_search

import com.softsquared.instagramlagame.config.ApplicationClass
import com.softsquared.instagramlagame.src.main.search.SearchRetrofitInterface
import com.softsquared.instagramlagame.src.main.search.auto_search.models.AutoSearchResponse
import retrofit2.Call
import retrofit2.Response

class AutoSearchService (val autoSearchInterface: AutoSearchInterface) {

    fun tryGetAutoSearch(getName: String, getPage: Int){
        val searchRetrofitInterface = ApplicationClass.sRetrofit.create(SearchRetrofitInterface::class.java)
        searchRetrofitInterface.getAutoSearch(getName, getPage).enqueue(object : retrofit2.Callback<AutoSearchResponse>{
            override fun onResponse(
                call: Call<AutoSearchResponse>,
                response: Response<AutoSearchResponse>,
            ) {
                autoSearchInterface.onGetAutoSearchSuccess(response.body() as AutoSearchResponse)
            }

            override fun onFailure(call: Call<AutoSearchResponse>, t: Throwable) {
                autoSearchInterface.onGetAutoSearchFailure(t.message ?: "통신 오류")
            }

        })
    }
}