package com.softsquared.instagramlagame.src.main.shopping

import com.softsquared.instagramlagame.src.main.shopping.models.ShoppingListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ShoppingRetrofitInterface {

    @GET("/shops/getList")
    fun getShoppingList(@Query("cursor") page: Int) : Call<ShoppingListResponse>

}