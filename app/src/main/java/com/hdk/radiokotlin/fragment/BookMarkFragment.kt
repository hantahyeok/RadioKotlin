package com.hdk.radiokotlin.fragment

import android.annotation.SuppressLint
import android.media.AudioManager
import android.media.MediaPlayer
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.Toast
import com.hdk.radiokotlin.DBHelper
import com.hdk.radiokotlin.MainActivity
import com.hdk.radiokotlin.R
import com.hdk.radiokotlin.SharedPreferencesUtil
import com.hdk.radiokotlin.adapter.MyAdapter
import com.hdk.radiokotlin.data.RadioStation
import com.hdk.radiokotlin.databinding.FragmentBookMarkBinding
import com.hdk.radiokotlin.databinding.FragmentRadioBinding
import java.io.IOException

class BookMarkFragment : Fragment(), MyAdapter.ItemClickListener {

    private lateinit var binding: FragmentBookMarkBinding

    var items = mutableListOf<RadioStation>()

    // DBHelper 객체 선언
//    private lateinit var dbHelper: DBHelper
    private lateinit var adapter: MyAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBookMarkBinding.inflate(layoutInflater)

        items.add(RadioStation(name = "d", favicon = "d", url = "fdf", url_resolved = "fd"))

        if(items.isNotEmpty()){
            binding.tv.visibility = View.INVISIBLE
        }

        adapter = MyAdapter(requireContext(), items, this)
        binding.recyclerView.adapter = adapter

        return binding.root
    }

    override fun onItemClick(url: String, favicon: String, name: String, url_resolved: String) {
        val mainActivity = activity as MainActivity
        mainActivity.getMedia(favicon, name, url, url_resolved)
        Toast.makeText(requireContext(), "Hello", Toast.LENGTH_SHORT).show()
    }
}