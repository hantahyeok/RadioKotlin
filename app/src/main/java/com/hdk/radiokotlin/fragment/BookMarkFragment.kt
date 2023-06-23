package com.hdk.radiokotlin.fragment

import android.annotation.SuppressLint
import android.media.AudioManager
import android.media.MediaPlayer
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
import com.hdk.radiokotlin.adapter.MyAdapter
import com.hdk.radiokotlin.data.RadioStation
import com.hdk.radiokotlin.databinding.FragmentBookMarkBinding
import com.hdk.radiokotlin.databinding.FragmentRadioBinding
import java.io.IOException

class BookMarkFragment : Fragment(), MyAdapter.ItemClickListener {

    private lateinit var binding: FragmentBookMarkBinding

    var items = mutableListOf<RadioStation>()

    // DBHelper 객체 선언
    private lateinit var dbHelper: DBHelper


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBookMarkBinding.inflate(layoutInflater)

        // DBHelper 객체 초기화
        dbHelper = DBHelper(requireContext())

        items = loadBookmarks() as MutableList<RadioStation>/**/

        var adapter = MyAdapter(requireContext(), items, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
        adapter.notifyDataSetChanged()
        binding.recyclerView.adapter = adapter

        return binding.root
    }

    private fun loadBookmarks(): List<RadioStation> {
        val bookmarks = mutableListOf<RadioStation>()
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            DBHelper.COLUMN_NAME,
            DBHelper.COLUMN_FAVICON,
            DBHelper.COLUMN_URL,
            DBHelper.COLUMN_URL_RESOLVED
        )
        val cursor = db.query(
            DBHelper.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )
        with(cursor) {
            while (moveToNext()) {
                val name = getString(getColumnIndexOrThrow(DBHelper.COLUMN_NAME))
                val favicon = getString(getColumnIndexOrThrow(DBHelper.COLUMN_FAVICON))
                val url = getString(getColumnIndexOrThrow(DBHelper.COLUMN_URL))
                val urlResolved = getString(getColumnIndexOrThrow(DBHelper.COLUMN_URL_RESOLVED))
                bookmarks.add(RadioStation(name, favicon, url, urlResolved))
            }
        }
        cursor.close()
        db.close()
        return bookmarks
    }

    override fun onItemClick(url: String, favicon: String, name: String, url_resolved: String) {
        val mainActivity = activity as MainActivity
        mainActivity.getMedia(favicon, name, url, url_resolved)
    }
}