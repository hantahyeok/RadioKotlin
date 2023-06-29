package com.hdk.radiokotlin

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import java.io.IOException

class MusicService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.e("test", "testtttt")
        Toast.makeText(this, "Heolo?", Toast.LENGTH_SHORT).show()

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}