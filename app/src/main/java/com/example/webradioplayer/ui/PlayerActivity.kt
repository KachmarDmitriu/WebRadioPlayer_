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
import com.google.android.exoplayer2.SimpleExoPlayer


class PlayerActivity : AppCompatActivity()
{

    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        PlayerActivityBinding.inflate(layoutInflater)
    }

    private var player: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L

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



    //    mediaUrlList.add("http://streams.rpr1.de/rpr-metal-128-mp3?usid=0-0-H-M-D-45")
    //    mediaUrlList.add("https://storage.googleapis.com/exoplayer-test-media-0/Jazz_In_Paris.mp3")
    //    mediaUrlList.add("http://listen.radionomy.com/goth-n-metal.m3u")
    //    mediaUrlList.add("http://streams.deltaradio.de/delta-foehnfrisur/mp3-192/itunes/play.pls")


    private fun onPlay() {
        if (mBound) {
            val mediaItem = MediaItem.fromUri(getString(R.string.media_url_1))

            playerService.play(mediaItem)


/*
            // Replaces the playlist with a new one.
            val newItems: List<MediaItem> = ImmutableList.of(
                MediaItem.fromUri(getString(R.string.media_url_2)),
                MediaItem.fromUri(getString(R.string.media_url_3)),
                MediaItem.fromUri(getString(R.string.media_url_4)),
                MediaItem.fromUri(getString(R.string.media_url_mp4))
            )
            player!!.setMediaItems(newItems, true)
            = */


            Toast.makeText(
                this,
                "playing: ${mediaItem}",// временно заменено для проверки, но это не подтягивает имя стануии playerService.currentMediaItem}",
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


        private fun initializePlayer() {


            //     val trackSelector = DefaultTrackSelector(this).apply {
            //         setParameters(buildUponParameters().setMaxVideoSizeSd())
            //     }

            player = SimpleExoPlayer.Builder(this)
                .build()
                .also { exoPlayer ->
                    viewBinding.videoView.player = exoPlayer

                    val mediaItem = MediaItem.fromUri(getString(R.string.media_url_mp4))
                    exoPlayer.setMediaItem(mediaItem)
                    val secondMediaItem = MediaItem.fromUri(getString(R.string.media_url_mp3))
                    exoPlayer.addMediaItem(secondMediaItem)

                    //     val mediaItem = MediaItem.Builder()
                    //        .setUri(getString(R.string.media_url_dash))
                    //        .setMimeType(MimeTypes.APPLICATION_MPD)
                    //        .build()


                    exoPlayer.playWhenReady = playWhenReady
                    exoPlayer.seekTo(currentWindow, playbackPosition)
                    exoPlayer.prepare()
                }


        }

        private fun releasePlayer() {
            player?.run {
                playbackPosition = this.currentPosition
                currentWindow = this.currentWindowIndex
                playWhenReady = this.playWhenReady
                release()
            }
            player = null
        }

        @SuppressLint("InlinedApi")
        private fun hideSystemUi() {
            viewBinding.videoView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        }

 */
