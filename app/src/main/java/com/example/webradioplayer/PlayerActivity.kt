package com.example.webradioplayer

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Binder
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.webradioplayer.databinding.ActivityPlayerBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.exoplayer2.util.Util


class PlayerActivity : AppCompatActivity()
{

    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityPlayerBinding.inflate(layoutInflater)
    }

    private var player: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L



    private lateinit var mService: PlayerNotificationService // LocalService
    private var mBound: Boolean = false

    /** Defines callbacks for service binding, passed to bindService()*/
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as PlayerNotificationService.LocalBinder   // LocalService.LocalBinder
            mService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

            /*
        val intent = Intent(this, PlayerNotificationService::class.java)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
*/

    }





        public override fun onStart() {
            super.onStart()

            // Bind to LocalService
            Intent(this, PlayerNotificationService::class.java).also { intent ->
                bindService(intent, connection, Context.BIND_AUTO_CREATE)
            }
                //if (Util.SDK_INT > 23) {
            //    initializePlayer()
            //}
        }

    /*    public override fun onResume() {
            super.onResume()
            hideSystemUi()
            if (Util.SDK_INT <= 23 || player == null) {
                initializePlayer()
            }
        }
*/
  /*      public override fun onPause() {
            super.onPause()
            if (Util.SDK_INT <= 23) {
                releasePlayer()
            }
        }
*/
        public override fun onStop() {
            super.onStop()

        unbindService(connection)
        mBound = false

        // if (Util.SDK_INT > 23) {
           //     releasePlayer()
           // }
        }

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


}