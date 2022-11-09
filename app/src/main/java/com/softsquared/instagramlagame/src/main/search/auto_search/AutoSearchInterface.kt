package com.softsquared.instagramlagame.src.main.search.auto_search

import com.softsquared.instagramlagame.src.main.search.auto_search.models.AutoSearchResponse

interface AutoSearchInterface {

    fun onGetAutoSearchSuccess(response: AutoSearchResponse)

    fun onGetAutoSearchFailure(message: String)
}