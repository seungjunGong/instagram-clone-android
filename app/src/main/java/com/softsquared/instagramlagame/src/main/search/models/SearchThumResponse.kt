package com.softsquared.instagramlagame.src.main.search.models


import com.google.gson.annotations.SerializedName
import com.softsquared.instagramlagame.config.BaseResponse

data class SearchThumResponse(
    @SerializedName("result")
    val resultSearchThum: List<ResultSearchThum>
) : BaseResponse()