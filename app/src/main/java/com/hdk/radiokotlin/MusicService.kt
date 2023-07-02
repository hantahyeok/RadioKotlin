package com.hdk.radiokotlin

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import com.hdk.radiokotlin.databinding.ActivityMainBinding
import com.hdk.radiokotlin.databinding.FragmentBookMarkBinding
import java.io.IOException
import java.util.zip.Inflater
import kotlin.math.log

class MusicService : Service() {


    lateinit var mediaPlayer : MediaPlayer
    lateinit var url : String

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        var intent : Intent = Intent()
        url = intent?.getStringExtra("url").toString()

        Log.i("dahyeok", url)

        mediaPlayer = MediaPlayer()

        Toast.makeText(this, "$url", Toast.LENGTH_SHORT).show()

        try {
            mediaPlayer.setDataSource(url)
            mediaPlayer.setOnPreparedListener { mp ->
                mp.setVolume(1.0f, 1.0f)
                mp.start() // 준비가 완료되면 재생 시작
                Toast.makeText(this, "음악 플레이중...", Toast.LENGTH_SHORT).show()
            }
            mediaPlayer.prepareAsync()
        } catch (e: IOException) {
            Log.e("MediaPlayer", "Failed to set data source: ${e.message}")
            e.printStackTrace()
        }

        }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer = MediaPlayer.create(this, R.raw.mp3)
        mediaPlayer.start()

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}