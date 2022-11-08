package com.softsquared.instagramlagame.src.main.home.post

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.instagramlagame.databinding.GridItemBinding

class PostPostingRVAdapter(private val uriArr : ArrayList<Uri>): RecyclerView.Adapter<PostPostingRVAdapter.ViewHolder>() {

    inner class ViewHolder {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: GridItemBinding = GridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        // 아이템 레이아웃을 바인딩하여 연결 후 ViewHolder 클래스에 연결해준다.
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}