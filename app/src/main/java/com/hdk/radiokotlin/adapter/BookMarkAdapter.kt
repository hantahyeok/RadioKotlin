package com.hdk.radiokotlin.adapter

import android.content.Context
import android.graphics.PorterDuff
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hdk.radiokotlin.R
import com.hdk.radiokotlin.data.RadioStation
import com.hdk.radiokotlin.databinding.RecyclerviewItemBinding
import java.io.IOException
import java.security.AccessController.getContext
import kotlin.concurrent.thread

class BookMarkAdapter constructor(var context: Context, var items: MutableList<RadioStation>, private val itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<BookMarkAdapter.VH>() {


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
            .placeholder(R.drawable.noimage) // 기본 이미지 리소스
            .error(R.drawable.noimage) // 에러 시 대체 이미지 리소스
            .into(holder.binding.iv)

        holder.binding.recyclerItem.setOnClickListener {


            itemClickListener.onItemClick(item.url_resolved, item.favicon, item.name, item.url_resolved)
//            holder.binding.iv.foreground = ContextCompat.getDrawable(context, R.drawable.bg_select)
        }

    }

    interface ItemClickListener {
        fun onItemClick(url: String, favicon: String, name: String, url_resolved: String)
    }

    override fun getItemCount(): Int = items.size

}