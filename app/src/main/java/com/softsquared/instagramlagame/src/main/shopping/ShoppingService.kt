package com.softsquared.instagramlagame.src.main.shopping

import com.softsquared.instagramlagame.config.ApplicationClass
import com.softsquared.instagramlagame.src.main.shopping.models.ShoppingListResponse
import retrofit2.Call
import retrofit2.Response

class ShoppingService (val shoppingFragmentInterface: ShoppingFragmentInterface) {

    fun tryGetShoppingList(getPage : Int){
        val shoppingRetrofitInterface = ApplicationClass.sRetrofit.create(ShoppingRetrofitInterface::class.java)
        shoppingRetrofitInterface.getShoppingList(getPage).enqueue(object : retrofit2.Callback<ShoppingListResponse>{
            override fun onResponse(
                call: Call<ShoppingListResponse>,
                response: Response<ShoppingListResponse>,
            ) {
                shoppingFragmentInterface.onGetShoppingListSuccess(response.body() as ShoppingListResponse)
            }

            override fun onFailure(call: Call<ShoppingListResponse>, t: Throwable) {
                shoppingFragmentInterface.onGetShoppingListFailure(t.message ?: "통신 오류")
            }

        })
    }
}