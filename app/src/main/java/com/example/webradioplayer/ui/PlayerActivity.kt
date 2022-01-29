package com.example.webradioplayer.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.webradioplayer.PlayerService
import com.example.webradioplayer.R
import com.example.webradioplayer.databinding.PlayerActivityBinding
import com.google.android.exoplayer2.MediaItem


class PlayerActivity : AppCompatActivity()
{

    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        PlayerActivityBinding.inflate(layoutInflater)
    }

    private lateinit var playerService: PlayerService
    private lateinit var binding: PlayerActivityBinding
    private var mBound: Boolean = false

    /** Defines callbacks for service binding, passed to bindService()*/
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {

            val binder = service as PlayerService.LocalBinder
            playerService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PlayerActivityBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setContentView(binding.root)

        setupListeners()

    }


    private fun setupListeners() {
        binding.buttonPlay.setOnClickListener { onPlay() }
        binding.buttonStop.setOnClickListener { onStopPlaying() }
    }


        public override fun onStart() {
            super.onStart()

            // Bind to LocalService
            Intent(this, PlayerService::class.java).also { intent ->
                bindService(intent, connection, Context.BIND_AUTO_CREATE)
            }
        }


        public override fun onStop() {
            super.onStop()

        unbindService(connection)
        mBound = false

        }


    private fun onPlay() {
        if (mBound) {
            val mediaItem = MediaItem.fromUri(getString(R.string.url_currentRadioStation))

            playerService.play(mediaItem)

            Toast.makeText(
                this,
                "playing: ${playerService.currentMediaItem}",// временно заменено для проверки, но это не подтягивает имя стануии ,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun onStopPlaying() {
        if (mBound) {
            playerService.stop()
            Toast.makeText(this, "stopping", Toast.LENGTH_SHORT).show()
        }


    }

}

/*
    //    mediaUrlList.add("http://streams.rpr1.de/rpr-metal-128-mp3?usid=0-0-H-M-D-45")
    //    mediaUrlList.add("https://storage.googleapis.com/exoplayer-test-media-0/Jazz_In_Paris.mp3")
    //    mediaUrlList.add("http://listen.radionomy.com/goth-n-metal.m3u")
    //    mediaUrlList.add("http://streams.deltaradio.de/delta-foehnfrisur/mp3-192/itunes/play.pls")
            = */
