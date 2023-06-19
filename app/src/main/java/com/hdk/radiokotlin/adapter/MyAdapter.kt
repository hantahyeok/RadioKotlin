package com.hdk.radiokotlin.adapter

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hdk.radiokotlin.R
import com.hdk.radiokotlin.data.RadioStation
import com.hdk.radiokotlin.databinding.RecyclerviewItemBinding
import kotlin.concurrent.thread

class MyAdapter constructor(var context: Context, var items: MutableList<RadioStation>) :
    RecyclerView.Adapter<MyAdapter.VH>() {

    inner class VH constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: RecyclerviewItemBinding = RecyclerviewItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView: View =
            LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        var item = items.get(position)
        holder.binding.tv.text = item.name
        Glide.with(context)
            .load(item.favicon)
            .placeholder(R.drawable.item_star_black) // 기본 이미지 리소스
            .error(R.drawable.item_star) // 에러 시 대체 이미지 리소스
            .into(holder.binding.iv)


        holder.binding.recyclerItem.setOnClickListener {
//
//            val url = item.url // your URL here
//            val mediaPlayer: MediaPlayer? = MediaPlayer().apply {
//                setAudioStreamType(AudioManager.STREAM_MUSIC)
//                setDataSource(url)
//                setOnPreparedListener {
//                    // 준비 완료 후 실행할 작업
//                    start() // 재생 시작
//                }
//                prepareAsync() // 비동기적으로 미디어 준비
//            }
        }

    }

    override fun getItemCount(): Int = items.size

}