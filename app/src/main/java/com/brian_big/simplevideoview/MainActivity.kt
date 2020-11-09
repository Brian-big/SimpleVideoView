package com.brian_big.simplevideoview

import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView

class MainActivity : AppCompatActivity() {
    private val VIDEO_SAMPLE: String = "tacoma_narrows"
    private val PLAYBACK_TIME: String = "play_time"
    private var currentPosition: Int = 0
    lateinit var videoView: VideoView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        videoView = findViewById(R.id.videoview)
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(PLAYBACK_TIME)
        }
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(PLAYBACK_TIME, videoView.currentPosition)
    }
    private fun getMedia(mediaName: String): Uri {
        return Uri.parse("android.resource://" + packageName +
                "/raw/" + mediaName)
    }
    private fun initializePlayer(){
        val uri = getMedia(VIDEO_SAMPLE)
        videoView.setVideoURI(uri)
        if (currentPosition > 0)
            videoView.seekTo(currentPosition)
        else videoView.seekTo(1)
        videoView.start()

        videoView.setOnCompletionListener {
            Toast.makeText(this, "Playback Completed", Toast.LENGTH_SHORT).show()
            videoView.seekTo(1)
        }
    }
    private fun releasePlayer(){
        videoView.stopPlayback()
    }
}