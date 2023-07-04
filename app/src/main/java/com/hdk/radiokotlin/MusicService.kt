package com.hdk.radiokotlin

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
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


    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return MusicBinder()
    }

    inner class MusicBinder : Binder() {
        fun getService(): MusicService {
            return this@MusicService
        }
    }

    fun startMusic(url: String) {
        if (!mediaPlayer.isPlaying) {

            mediaPlayer.stop()
            mediaPlayer.reset()

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
    }

    // 음악 정지 메서드
    fun stopMusic() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
    }

    // MainActivity로 메시지를 보내는 함수
//    private fun play(isPlaying: Boolean) {
//        val intent = Intent(this, MainActivity::class.java)
//        intent.putExtra("play", isPlaying)
//
//    }

}