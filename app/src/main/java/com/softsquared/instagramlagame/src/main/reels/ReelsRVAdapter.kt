package com.softsquared.instagramlagame.src.main.reels

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlaybackException
import com.master.exoplayer.ExoPlayerHelper
import com.softsquared.instagramlagame.R
import com.softsquared.instagramlagame.databinding.ReelsItemBinding

class ReelsRVAdapter(private val list: ArrayList<String>): RecyclerView.Adapter<ReelsRVAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ReelsItemBinding = ReelsItemBinding .inflate(LayoutInflater.from(parent.context), parent, false)
        // 아이템 레이아웃을 바인딩하여 연결 후 ViewHolder 클래스에 연결해준다.
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
        holder.binding.masterExoPlayer.url = list[position]
    }

    override fun getItemCount(): Int = list.size





    inner class ViewHolder(val binding: ReelsItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: String){
            // 아이템 레이아웃에서 정한 값들을 데이터클래스 값으로 넣어줌

            binding.masterExoPlayer.imageView = binding.reelsLoading

            //Inside onBindViewHolder of your RecyclerViewAdapter

            binding.masterExoPlayer.listener = object : ExoPlayerHelper.Listener {
                override fun onError(error: ExoPlaybackException?) {
                    super.onError(error)
                    Log.d("master","$error")
                    if(error == null){
                        binding.reelsSideBtLayout.visibility = View.GONE
                        binding.reelsProfileLayout.visibility =  View.GONE
                    }

                }

                override fun onProgress(positionMs: Long) {
                    super.onProgress(positionMs)
                }
                //Listen for buffering listener
                override fun onBuffering(isBuffering: Boolean) {
                    super.onBuffering(isBuffering)
                    if(isBuffering){
                        binding.reelsSideBtLayout.visibility = View.GONE
                        binding.reelsProfileLayout.visibility =  View.GONE
                    } else{
                        binding.reelsSideBtLayout.visibility = View.VISIBLE
                        binding.reelsProfileLayout.visibility =  View.VISIBLE
                    }

                }

                //Update mute/unmute icon on player ready callback.

                override fun onPlayerReady() {
                    super.onPlayerReady()
//                    binding.masterExoPlayer.visibility = View.VISIBLE
//                    if (binding.masterExoPlayer.isMute) {
//                        binding.ivVolume.setImageResource(R.drawable.ic_volume_off)
//                    } else {
//                        binding.ivVolume.setImageResource(R.drawable.ic_volume_on)
//                    }
                }

                override fun onStop() {
                    super.onStop()
//                    binding.ivVolume.visibility = View.GONE
                }
            }
        }


    }



}