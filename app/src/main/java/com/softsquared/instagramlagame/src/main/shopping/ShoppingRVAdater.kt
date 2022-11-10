package com.softsquared.instagramlagame.src.main.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.softsquared.instagramlagame.databinding.GridItemBinding
import com.softsquared.instagramlagame.databinding.ShoppingGridItemBinding
import com.softsquared.instagramlagame.src.main.shopping.models.ResultShoppingList

class ShoppingRVAdater(private val list: ArrayList<ResultShoppingList>): RecyclerView.Adapter<ShoppingRVAdater.ViewHolder>() {

    inner class ViewHolder(private val binding: ShoppingGridItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: ResultShoppingList){
            Glide.with(binding.shoppingGridItemIv.context)
                .load(data.thumbnailUrl)
                .apply(RequestOptions.centerCropTransform())
                .error(  "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRc1GXYCnH18YeoejkENhnggCRkj4osY0G_RQ&usqp=CAU")
                .into(binding.shoppingGridItemIv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ShoppingGridItemBinding = ShoppingGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

}