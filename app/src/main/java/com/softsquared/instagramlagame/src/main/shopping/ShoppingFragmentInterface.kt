package com.softsquared.instagramlagame.src.main.shopping

import com.softsquared.instagramlagame.src.main.shopping.models.ShoppingListResponse

interface ShoppingFragmentInterface {

    fun onGetShoppingListSuccess(response: ShoppingListResponse)

    fun onGetShoppingListFailure(message: String)
}