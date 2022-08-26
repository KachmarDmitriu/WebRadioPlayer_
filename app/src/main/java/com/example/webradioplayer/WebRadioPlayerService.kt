package com.example.webradioplayer

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.upstream.DataSource


class PlayerService : Service(), AudioManager.OnAudioFocusChangeListener {

   private lateinit var dataSourceFactory: DataSource.Factory
    private val logTag = PlayerService::class.simpleName
    private var isForegroundService = false
    private lateinit var mPlayer: ExoPlayer
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var playerNotificationManager: CustomPlayerNotificationManager

    var currentMediaItem: MediaItem? = null
        private set

    private val binder = LocalBinder()


    inner class LocalBinder : Binder() {
        fun getService(): PlayerService = this@PlayerService
    }


    override fun onBind(intent: Intent): IBinder {
        return binder
    }


    override fun onCreate() {
        super.onCreate()

        val sessionActivityPendingIntent =
            packageManager?.getLaunchIntentForPackage(packageName)?.let { sessionIntent ->
                PendingIntent.getActivity(
                    this,
                    0,
                    sessionIntent,
                    0
                )
            }

        mediaSession = MediaSessionCompat(this, "MusicService")
            .apply {
                setSessionActivity(sessionActivityPendingIntent)
                isActive = true
            }

        playerNotificationManager = CustomPlayerNotificationManager(
            this,
            PlayerNotificationListener()
        )
        mPlayer = ExoPlayer.Builder(this).build()
   }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }


    override fun onDestroy() {
        playerNotificationManager.hideNotification()

        mPlayer.release()
        super.onDestroy()
    }


    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopSelf()
    }


    private inner class PlayerNotificationListener :
        PlayerNotificationManager.NotificationListener {
        override fun onNotificationPosted(
            notificationId: Int,
            notification: Notification,
            ongoing: Boolean
        ) {
            if (ongoing && !isForegroundService) {
                ContextCompat.startForegroundService(
                    applicationContext,
                    Intent(applicationContext, this@PlayerService.javaClass)
                )

                startForeground(notificationId, notification)
                isForegroundService = true
            }
        }

        override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
            stopForeground(true)
            isForegroundService = false
            stopSelf()
        }
    }


              fun play(mediaItem: MediaItem) {

                currentMediaItem = mediaItem

                mPlayer.setMediaItem(currentMediaItem!!)
                mPlayer.playWhenReady = true
                mPlayer.prepare()

                playerNotificationManager.showNotificationForPlayer(mPlayer)

                Log.d(logTag, "$currentMediaItem")
            }

            fun stop() {
                currentMediaItem = null
                mPlayer.stop()
                playerNotificationManager.hideNotification()

                Log.d(logTag, "stop")
            }


    override fun onAudioFocusChange(focusState: Int) {
        //Invoked when the audio focus of the system is updated.
        when (focusState) {
            AudioManager.AUDIOFOCUS_GAIN -> {
                // resume playback
                if (mPlayer == null) onCreate() else if (!mPlayer.isPlaying()) mPlayer.play()
                mPlayer.setVolume(1.0f)
            }
            AudioManager.AUDIOFOCUS_LOSS -> {
                // Lost focus for an unbounded amount of time: stop playback and release media player
                if (mPlayer.isPlaying()) mPlayer.stop()
                mPlayer.release()
             //   mPlayer = null
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ->             // Lost focus for a short time, but we have to stop
                // playback. We don't release the media player because playback
                // is likely to resume
                if (mPlayer.isPlaying()) mPlayer.pause()
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK ->             // Lost focus for a short time, but it's ok to keep playing
                // at an attenuated level
                if (mPlayer.isPlaying()) mPlayer.setVolume(0.1f)
        }
    }


}