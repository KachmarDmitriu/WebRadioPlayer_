package com.example.webradioplayer

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.webradioplayer.databinding.ActivityPlayerBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer


class PlayerActivity : AppCompatActivity()
{

    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityPlayerBinding.inflate(layoutInflater)
    }

    private var player: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L

    private lateinit var playerService: PlayerService

    private lateinit var binding: ActivityPlayerBinding

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

        binding = ActivityPlayerBinding.inflate(layoutInflater)
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
            val mediaItem = MediaItem.fromUri(getString(R.string.media_url_mp3))

            playerService.play(mediaItem)

            Toast.makeText(
                this,
                "playing: ${playerService.currentMediaItem}",
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
