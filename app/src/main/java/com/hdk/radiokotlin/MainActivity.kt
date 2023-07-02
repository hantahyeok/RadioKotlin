package com.hdk.radiokotlin

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hdk.radiokotlin.adapter.MyAdapter
import com.hdk.radiokotlin.data.RadioStation
import com.hdk.radiokotlin.databinding.ActivityMainBinding
import com.hdk.radiokotlin.fragment.BookMarkFragment
import com.hdk.radiokotlin.fragment.PodcastFragment
import com.hdk.radiokotlin.fragment.RadioFragment
import com.hdk.radiokotlin.fragment.SettingFragment
import java.io.IOException


class MainActivity : AppCompatActivity() {


    // DBHelper 객체 선언
    private lateinit var dbHelper: DBHelper

    private var currentFragment: Fragment? = null

    lateinit var binding: ActivityMainBinding

    private val radioFragment by lazy { RadioFragment() }
    private val bookMarkFragment by lazy { BookMarkFragment() }
    private val podcastFragment by lazy { PodcastFragment() }
    private val settingFragment by lazy { SettingFragment() }

    private lateinit var mediaPlayer: MediaPlayer

    var isPlay: Boolean = false
    var isStar: Boolean = false

    lateinit var favicon : String
    lateinit var name : String
    lateinit var url : String
    lateinit var url_resolved : String

    var items = mutableListOf<RadioStation>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // DBHelper 객체 초기화
        dbHelper = DBHelper(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mediaPlayer = MediaPlayer()

        isPlay = false
        isStar = false

        val bnv_main = binding.bnvMain

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
//                    R.id.action_podcast -> {
//                        changeFragment(podcastFragment)
//                        // Respond to navigation item 3 click
//                    }
                    R.id.action_setup -> {
                        changeFragment(settingFragment)
                        // Respond to navigation item 4 click
                    }
                }
            true
        }
        bnv_main.selectedItemId = R.id.action_radio

        binding.starBtn.setOnClickListener {

            items.add(RadioStation(name, favicon, url, url_resolved))

            if(isStar == false){

                // 즐겨찾기 삭제
//                delete()

                val animator = ValueAnimator.ofFloat(0f, 1f).setDuration(1000)
                animator.addUpdateListener { animation ->
                    binding.starBtn.setProgress(animation.animatedValue as Float)
                }
                animator.start()
                isStar = true

            }else{
//
//                //즐겨찾기 추가
//                save()

                val animator = ValueAnimator.ofFloat(1f, 0.1f).setDuration(1000)
                animator.addUpdateListener { animation ->
                    binding.starBtn.setProgress(animation.animatedValue as Float)
                }
                animator.start()
                isStar = false

            }

        }


        binding.playBtn.setOnClickListener {

//            stop()
            if(isPlay == false){ // stop..

                mediaPlayer.stop()
                mediaPlayer.reset()

                binding.progressBar.visibility = View.INVISIBLE

                val animator = ValueAnimator.ofFloat(0.5f, 1f).setDuration(1000)
                animator.addUpdateListener { animation ->
                    binding.playBtn.setProgress(animation.animatedValue as Float)
                }
                animator.start()
                isPlay = true

            }else{ // play..

                mediaPlayer.stop()
                mediaPlayer.reset()

                binding.progressBar.visibility = View.VISIBLE


//                val intent : Intent = Intent(this, MusicService::class.java)
//                intent.putExtra("url", url)
//                startService(intent)

                try {
                    mediaPlayer.setDataSource(url)
                    mediaPlayer.setOnPreparedListener { mp ->
                        mp.setVolume(1.0f, 1.0f)
                        mp.start() // 준비가 완료되면 재생 시작
                        Toast.makeText(this, "음악 플레이중...", Toast.LENGTH_SHORT).show()
                        binding.progressBar.visibility = View.INVISIBLE
                    }
                    mediaPlayer.prepareAsync()
                } catch (e: IOException) {
                    Log.e("MediaPlayer", "Failed to set data source: ${e.message}")
                    e.printStackTrace()
                    binding.progressBar.visibility = View.INVISIBLE

                }

                val animator = ValueAnimator.ofFloat(0f, 0.5f).setDuration(1000)
                animator.addUpdateListener { animation ->
                    binding.playBtn.setProgress(animation.animatedValue as Float)
                }
                animator.start()
                isPlay = false
            }

        }

    }// onCreate...

    fun save(){
//        val sharedPreferences = applicationContext.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
    }

    fun delete(){

    }

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


    fun getMedia(favicon: String, name: String, url: String, url_resolved: String){
        binding.progressBar.visibility = View.VISIBLE

        mediaPlayer.stop()
        mediaPlayer.reset()

        try {
            mediaPlayer.setDataSource(url)
            mediaPlayer.setOnPreparedListener { mp ->
                mp.setVolume(1.0f, 1.0f)
                mp.start() // 준비가 완료되면 재생 시작
                Toast.makeText(this, "음악 플레이중...", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.INVISIBLE
            }
            mediaPlayer.prepareAsync()
        } catch (e: IOException) {
            binding.progressBar.visibility = View.INVISIBLE
            Log.e("MediaPlayer", "Failed to set data source: ${e.message}")
            e.printStackTrace()
        }

//        val intent = Intent(this, MusicService::class.java)
//        intent.putExtra("url", url)
//        startService(intent)


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
    }

}