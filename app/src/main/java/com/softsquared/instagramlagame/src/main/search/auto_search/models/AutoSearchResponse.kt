package com.softsquared.instagramlagame.src.main.search.auto_search.models


import com.google.gson.annotations.SerializedName
import com.softsquared.instagramlagame.config.BaseResponse

data class AutoSearchResponse(
    @SerializedName("result")
    val resultAutoSearch: List<ResultAutoSearch>
) : BaseResponse()