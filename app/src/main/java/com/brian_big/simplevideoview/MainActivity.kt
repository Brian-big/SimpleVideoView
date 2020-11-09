package com.brian_big.simplevideoview

import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView

class MainActivity : AppCompatActivity() {
    private val VIDEO_SAMPLE: String = "tacoma_narrows"
    lateinit var videoView: VideoView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        videoView = findViewById(R.id.videoview)
        val controller = MediaController(this)
        controller.setMediaPlayer(videoView)
        videoView.setMediaController(controller)
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
            videoView.pause()
    }
    private fun getMedia(mediaName: String): Uri {
        return Uri.parse("android.resource://" + packageName +
                "/raw/" + mediaName)
    }
    private fun initializePlayer(){
        val uri = getMedia(VIDEO_SAMPLE)
        videoView.setVideoURI(uri)
        videoView.start()
    }
    private fun releasePlayer(){
        videoView.stopPlayback()
    }
}