package com.softsquared.instagramlagame.src.main.search.auto_search

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.databinding.ItemSerchBinding
import com.softsquared.instagramlagame.src.main.search.auto_search.models.ResultAutoSearch

class AutoSearchRVAdapter(private val searchData: ArrayList<ResultAutoSearch>) : RecyclerView.Adapter<AutoSearchRVAdapter.ViewHolder>() {

    // 아이템 클릭시 interface로 클래스 선언
    interface ProfileItemClickListener{
        fun onItemClick(userNick : String, userId: Int)
    }
    // 전달된 객체를 저장할 변수 추가
    private lateinit var pItemClickListener: ProfileItemClickListener
    // 리스너 객체를 전달하는 메서드
    fun setProfilItemClickListener(itemClickListener: ProfileItemClickListener){
        pItemClickListener = itemClickListener
    }


    inner class ViewHolder(private val binding: ItemSerchBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ResultAutoSearch){
            if(data.storyStatus == "true"){
                Glide.with(binding.profileIv.context).load(data.profileUrl).error("https://firebasestorage.googleapis.com/v0/b/instagramlagame.appspot.com/o/ic_basic_profile.png?alt=media&token=6632dbfe-f55f-4692-b5b5-f3ee0cbb04cb").into(binding.profileIv)
            }else{
                Glide.with(binding.profileIv.context).load(data.profileUrl).error("https://firebasestorage.googleapis.com/v0/b/instagramlagame.appspot.com/o/ic_profile.png?alt=media&token=0053a8f4-3cdf-44b7-8dbe-768ac4d4bba4").into(binding.profileIv)
                binding.profileIv.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
            binding.profileNameTv.text = data.name
            binding.profileNickNameTv.text = data.nickname
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSerchBinding = ItemSerchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(searchData[position])

        // 리사이클러뷰 클릭 이벤트 안에 앞서 구현한 클릭 함수를 호출한다.
        holder.itemView.setOnClickListener{pItemClickListener.onItemClick(searchData[position].nickname, searchData[position].userId)}

    }

    override fun getItemCount(): Int = searchData.size

}