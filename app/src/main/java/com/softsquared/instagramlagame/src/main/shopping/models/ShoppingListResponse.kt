package com.softsquared.instagramlagame.src.main.shopping.models


import com.google.gson.annotations.SerializedName
import com.softsquared.instagramlagame.config.BaseResponse

data class ShoppingListResponse(
    @SerializedName("result")
    val result: ArrayList<ResultShoppingList>
) : BaseResponse()