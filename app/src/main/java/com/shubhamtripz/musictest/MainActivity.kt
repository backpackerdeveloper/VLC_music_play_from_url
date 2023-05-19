package com.shubhamtripz.musictest

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer

class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var playButton: Button
    private lateinit var seekBar: SeekBar
    private var isPlaying = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playButton = findViewById(R.id.playButton)
        seekBar = findViewById(R.id.seekBar)

        val audioUri = Uri.parse("https://drive.google.com/u/1/uc?id=1KzeCG3W7e3ii0GTYNqWGlwCiY01FGUdw&export=download") // Replace with the correct URL

        val libVLC = LibVLC(this)
        mediaPlayer = MediaPlayer(libVLC).apply {
            media = Media(libVLC, audioUri)
        }

        playButton.setOnClickListener {
            if (isPlaying) {
                mediaPlayer.pause()
                playButton.text = "Play"
                isPlaying = false
            } else {
                mediaPlayer.play()
                playButton.text = "Pause"
                isPlaying = true
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val position = (progress.toFloat() / seekBar.max) * mediaPlayer.length.toFloat()
                    mediaPlayer.time = position.toLong()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}