package com.hdk.radiokotlin

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hdk.radiokotlin.fragment.BookMarkFragment
import com.hdk.radiokotlin.fragment.PodcastFragment
import com.hdk.radiokotlin.fragment.RadioFragment
import com.hdk.radiokotlin.fragment.SettingFragment
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private val radioFragment by lazy { RadioFragment() }
    private val bookMarkFragment by lazy { BookMarkFragment() }
    private val podcastFragment by lazy { PodcastFragment() }
    private val settingFragment by lazy { SettingFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bnv_main = findViewById<BottomNavigationView>(R.id.bnv_main)

        //supportFragmentManager.beginTransaction().add(R.id.fl_con, NaviHomeFragment()).commit()

        bnv_main.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.action_radio -> {
                        changeFragment(radioFragment)
                        // Respond to navigation item 1 click
                    }
                    R.id.action_bookmark -> {
                        changeFragment(bookMarkFragment)
                        // Respond to navigation item 2 click
                    }
                    R.id.action_podcast -> {
                        changeFragment(podcastFragment)
                        // Respond to navigation item 3 click
                    }
                    R.id.action_setup -> {
                        changeFragment(settingFragment)
                        // Respond to navigation item 4 click
                    }
                }
            true
        }
        bnv_main.selectedItemId = R.id.action_radio
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }

}