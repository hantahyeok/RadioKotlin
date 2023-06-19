package com.hdk.radiokotlin

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.hdk.radiokotlin.databinding.RecyclerviewItemBinding

class MyAdapter constructor(var context: Context, var items: MutableList<RadioStation>): RecyclerView.Adapter<MyAdapter.VH>(){

    inner class VH constructor(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding: RecyclerviewItemBinding = RecyclerviewItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent,false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        var item = items.get(position)
//        holder.binding.iv = ""
        holder.binding.tv.text = item.name
    }

    override fun getItemCount(): Int = items.size

}