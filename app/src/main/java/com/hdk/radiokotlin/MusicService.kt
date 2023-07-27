package com.hdk.radiokotlin

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.Message
import android.transition.Transition
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.NotificationTarget
import com.hdk.radiokotlin.databinding.ActivityMainBinding
import com.hdk.radiokotlin.databinding.FragmentBookMarkBinding
import java.io.IOException
import java.util.zip.Inflater
import kotlin.math.log

class MusicService : Service() {


    lateinit var mediaPlayer: MediaPlayer
    private var playListener: Play? = null

    var url : String? = null
    var favicon : String? = null
    var name : String? = null

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()


    }

    fun setPlayListener(listener: Play) {
        playListener = listener
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

    @SuppressLint("RemoteViewLayout", "MissingPermission")
    fun startMusic(url: String, favicon: String, name: String) {
        if (!mediaPlayer.isPlaying) {

            this.url = url
            this.favicon = favicon
            this.name = name

            mediaPlayer.stop()
            mediaPlayer.reset()

            try {
                mediaPlayer.setDataSource(url)
                mediaPlayer.setOnPreparedListener { mp ->
                    mp.setVolume(1.0f, 1.0f)
                    mp.start() // 준비가 완료되면 재생 시작

                    Toast.makeText(this, "Music Start!", Toast.LENGTH_SHORT).show()
                    playListener?.InvisibleProgress() // MainActivity로 메시지 전달
                }
                mediaPlayer.prepareAsync()

            } catch (e: IOException) {
                Log.e("MediaPlayer", "Failed to set data source: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    interface Play {
        fun InvisibleProgress()
    }

    // 음악 정지 메서드
    fun stopMusic() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
    }

}