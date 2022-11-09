package com.softsquared.instagramlagame.src.main.search

import com.softsquared.instagramlagame.config.BaseResponse
import com.softsquared.instagramlagame.src.main.search.models.SearchThumResponse

interface SearchFragmentInterface {

    fun onGetSearchThumbnailSuccess(response: SearchThumResponse)

    fun onGetSearchThumbnailFailure(message: String)
}