package com.hdk.radiokotlin

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.RemoteViews
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hdk.radiokotlin.adapter.MyAdapter
import com.hdk.radiokotlin.data.RadioStation
import com.hdk.radiokotlin.databinding.ActivityMainBinding
import com.hdk.radiokotlin.databinding.FragmentRadioBinding
import com.hdk.radiokotlin.fragment.BookMarkFragment
import com.hdk.radiokotlin.fragment.PodcastFragment
import com.hdk.radiokotlin.fragment.RadioFragment
import com.hdk.radiokotlin.fragment.SettingFragment
import java.io.IOException
import java.time.LocalDateTime
import kotlin.math.log


class MainActivity : AppCompatActivity(), MusicService.Play {

    private var isServiceRunning: Boolean = false

    var db = DatabaseHelper(this)
    lateinit var all: String

    // DBHelper 객체 선언
    lateinit var dbHelper: DBHelper
    lateinit var database: SQLiteDatabase

    private var currentFragment: Fragment? = null

    lateinit var binding: ActivityMainBinding
    lateinit var binding2: FragmentRadioBinding

    private val radioFragment by lazy { RadioFragment() }
    private val bookMarkFragment by lazy { BookMarkFragment() }
    private val podcastFragment by lazy { PodcastFragment() }
    private val settingFragment by lazy { SettingFragment() }

    private lateinit var mediaPlayer: MediaPlayer

    var isPlay: Boolean = false
    var isStar: Boolean = false

    lateinit var favicon: String
    lateinit var name: String
    lateinit var url: String
    lateinit var url_resolved: String

    var items = mutableListOf<RadioStation>()

    private var isBound = false
    private var musicService: MusicService? = null


    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MusicService.MusicBinder
            musicService = binder.getService()
            musicService?.setPlayListener(this@MainActivity) // playListener 설정
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            musicService = null
            isBound = false
        }
    }


    @SuppressLint("MissingPermission", "RemoteViewLayout")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var i = DatabaseHelper(this).getAllData()
        Log.i("this", i)

        val intent = Intent(this, MusicService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)

        dbHelper = DBHelper(this, "newdb.db", null, 1)
        database = dbHelper.writableDatabase

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding2 = FragmentRadioBinding.inflate(layoutInflater)

        setContentView(binding.root)

        mediaPlayer = MediaPlayer()

        isPlay = false
        isStar = false

//        val bnv_main = binding.bnvMain

        //supportFragmentManager.beginTransaction().add(R.id.fl_con, NaviHomeFragment()).commit()

//        bnv_main.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.action_radio -> {
//                    changeFragment(RadioFragment())
//                    // Respond to navigation item 1 click
//                }
//
//                R.id.action_bookmark -> {
//                    changeFragment(BookMarkFragment())
//                    // Respond to navigation item 2 click
//                }
////                    R.id.action_podcast -> {
////                        changeFragment(podcastFragment)
////                        // Respond to navigation item 3 click
////                    }
//                R.id.action_setup -> {
//                    changeFragment(SettingFragment())
//                    // Respond to navigation item 4 click
//                }
//            }
//            true
//        }
//        bnv_main.selectedItemId = R.id.action_radio

//        binding.starBtn.setOnClickListener {
//
//            items.add(RadioStation(name, favicon, url, url_resolved))
//
//            check()
//
//        }


        binding.playBtn.setOnClickListener {

            mediaPlayer.stop()

            if (isPlay == false) { // stop..

                binding.progressBar.visibility = View.INVISIBLE
                musicService?.stopMusic()

                val animator = ValueAnimator.ofFloat(0.5f, 1f).setDuration(1000)
                animator.addUpdateListener { animation ->
                    binding.playBtn.setProgress(animation.animatedValue as Float)
                }
                animator.start()

                isPlay = true

            } else { // play..

                binding.progressBar.visibility = View.VISIBLE
                musicService?.startMusic(url, favicon, name)

                val animator = ValueAnimator.ofFloat(0f, 0.5f).setDuration(1000)
                animator.addUpdateListener { animation ->
                    binding.playBtn.setProgress(animation.animatedValue as Float)
                }
                animator.start()

                isPlay = false
            }

        }

    }// onCreate...

//    fun check(){
//        if (isStar == false) {
//            save()
//        } else if (isStar == true) {
//            delete()
//        }
//    }

//    fun save() {
//        //즐겨찾기 추가
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            db.insertInfo(name, url, url_resolved, favicon, LocalDateTime.now().toString())
//        }
//
//        val animator = ValueAnimator.ofFloat(0f, 1f).setDuration(1000)
//        animator.addUpdateListener { animation ->
//            binding.starBtn.setProgress(animation.animatedValue as Float)
//        }
//        animator.start()
//        isStar = true
//    }
//
//    fun delete() {
//        // 즐겨찾기 삭제
//        db.deleteData(name)
//
//        val animator = ValueAnimator.ofFloat(1f, 0.1f).setDuration(1000)
//        animator.addUpdateListener { animation ->
//            binding.starBtn.setProgress(animation.animatedValue as Float)
//        }
//        animator.start()
//        isStar = false
//    }

    private fun changeFragment(fragment: Fragment) {

//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.frame_layout, fragment)
//            .commit()

        val fragmentManager = supportFragmentManager.beginTransaction()

        if (currentFragment != null) {
            fragmentManager.hide(currentFragment!!)
        }

        if (fragment.isAdded) {
            fragmentManager.show(fragment)
        } else {
            fragmentManager.add(R.id.frame_layout, fragment)
        }

        fragmentManager.commit()

        currentFragment = fragment
    }


    private var CHANNEL_ID = "Your_Channel_ID"

    @SuppressLint("RemoteViewLayout")
    private fun showNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "App Notification"
            val descriptionText = "This is your notificationdescription"
            val importnace: Int = NotificationManager.IMPORTANCE_DEFAULT
            val channel: NotificationChannel =
                NotificationChannel(CHANNEL_ID, name, importnace).apply {
                    description = descriptionText
                }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    @SuppressLint("RemoteViewLayout", "MissingPermission")
    fun getMedia(favicon: String, name: String, url: String, url_resolved: String) {
        binding.progressBar.visibility = View.VISIBLE

        isPlay = false

        musicService?.stopMusic()
        musicService?.startMusic(url, favicon, name)


        val animator = ValueAnimator.ofFloat(0f, 0.5f).setDuration(1000)
        animator.addUpdateListener { animation ->
            binding.playBtn.setProgress(animation.animatedValue as Float)
        }
        animator.start()

        binding.linear.visibility = View.VISIBLE

        this.mediaPlayer = mediaPlayer
        this.favicon = favicon
        this.name = name
        this.url = url
        this.items = items
        this.url_resolved = url_resolved

        Glide.with(this)
            .load(favicon)
            .placeholder(R.drawable.noimage) // 기본 이미지 리소스
            .error(R.drawable.noimage) // 에러 시 대체 이미지 리소스
            .into(binding.iv)
        binding.tv.text = name
    }// getMedia...

    override fun onDestroy() {
        super.onDestroy()
        // 서비스와 언바인드
        unbindService(connection)
    }

    override fun InvisibleProgress() {
        binding.progressBar.visibility = View.INVISIBLE
    }

}