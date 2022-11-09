package com.softsquared.instagramlagame.src.main.home.post

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.parser.ColorParser
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.databinding.GridItemBinding

class PostPostingRVAdapter(private val uriArr: MutableList<Uri>): RecyclerView.Adapter<PostPostingRVAdapter.ViewHolder>() {


    private var oldClick = 0

    // 아이템 클릭시 interface로 클래스 선언
    interface GalleryItemClickListener{
        fun onItemClick(uri: Uri)
    }

    // 전달된 객체를 저장할 변수 추가
    private lateinit var gItemClickListener: GalleryItemClickListener
    // 리스너 객체를 전달하는 메서드
    fun setGalleryItemClickListener(itemClickListener: GalleryItemClickListener){
        gItemClickListener = itemClickListener
    }

    inner class ViewHolder(val binding:  GridItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: Uri){
            // 아이템 레이아웃에서 정한 값들을 데이터클래스 값으로 넣어줌
            binding.gridItemIv.setImageURI(data)
            binding.activeClick.visibility = View.GONE

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: GridItemBinding = GridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        // 아이템 레이아웃을 바인딩하여 연결 후 ViewHolder 클래스에 연결해준다.
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(uriArr[position])
        holder.binding.gridItemIv.setOnClickListener {
            gItemClickListener.onItemClick(uriArr[position])
            holder.binding.activeClick.visibility = View.VISIBLE
            notifyItemChanged(oldClick)
            oldClick = holder.adapterPosition
        }

    }



    override fun getItemCount(): Int= uriArr.size
}