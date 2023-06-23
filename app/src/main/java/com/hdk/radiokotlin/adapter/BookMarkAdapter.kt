package com.hdk.radiokotlin.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hdk.radiokotlin.data.RadioStation
import com.hdk.radiokotlin.databinding.RecyclerviewItemBinding

class BookMarkAdapter constructor(var context: Context, var items: MutableList<RadioStation>) : RecyclerView.Adapter<BookMarkAdapter.VH>() {

    inner class VH constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: RecyclerviewItemBinding = RecyclerviewItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        TODO("Not yet implemented")
    }
}