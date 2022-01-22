package com.example.webradioplayer

//import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.AudioManager.OnAudioFocusChangeListener
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.media.AudioAttributesCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.upstream.DataSource

class PlayerService : Service()
{
    private lateinit var dataSourceFactory: DataSource.Factory
    //    private lateinit var playerNotificationManager: PlayerNotificationManager
    private var audioManager: AudioManager? = null


    //  private var notificationId = 123;
    //    private var channelId = "channelId"


    private val logTag = PlayerService::class.simpleName

    private var isForegroundService = false

    private lateinit var mPlayer: ExoPlayer
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var playerNotificationManager: CustomPlayerNotificationManager

    var currentMediaItem: MediaItem? = null
        private set



    private val audioFocusChangeListener =
        OnAudioFocusChangeListener { focusChange ->
            when (focusChange) {
                AudioManager.AUDIOFOCUS_GAIN ->

                    mediaSessionCallback.Play()
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK ->

                    mediaSessionCallback.Play()
                else ->
                    mediaSessionCallback.Stop()
            }
        }





    // Binder given to clients
    private val binder = LocalBinder()

     /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    inner class LocalBinder : Binder() {
        fun getService(): PlayerService = this@PlayerService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }


    override fun onCreate() {
            super.onCreate()


       audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        val audioAttributes = AudioAttributesCompat.Builder()
            .setContentType(AudioAttributesCompat.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributesCompat.USAGE_MEDIA)
            .build()



        val sessionActivityPendingIntent =
                packageManager?.getLaunchIntentForPackage(packageName)?.let { sessionIntent ->
                    PendingIntent.getActivity(this, 0, sessionIntent, 0)
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

    /*

            val context = this
            mPlayer = SimpleExoPlayer.Builder(this).build()
            // Create a data source factory.
            dataSourceFactory = DefaultHttpDataSourceFactory(Util.getUserAgent(context, "app-name"))
            mPlayer.prepare(getListOfMediaSource())
            mPlayer.playWhenReady = true

            playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(
                this,
                channelId,
                R.string.channel_name,
                R.string.channel_desc,
                notificationId,
                object : PlayerNotificationManager.MediaDescriptionAdapter {



                    override fun createCurrentContentIntent(player: Player): PendingIntent? {
                        // return pending intent
                        val intent = Intent(context, PlayerActivity::class.java);
                        return PendingIntent.getActivity(
                            context, 0, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                        )
                    }

                    //pass description here
                    override fun getCurrentContentText(player: Player): String? {
                        return "Description"
                    }

                    //pass title (mostly playing audio name)
                    override fun getCurrentContentTitle(player: Player): String {
                        return "Title"
                    }

                    // pass image as bitmap
                    override fun getCurrentLargeIcon(
                        player: Player,
                        callback: PlayerNotificationManager.BitmapCallback
                    ): Bitmap? {
                        return null
                    }
                },
                object : PlayerNotificationManager.NotificationListener {

                    override fun onNotificationPosted(
                        notificationId: Int,
                        notification: Notification,
                        onGoing: Boolean) {

                        startForeground(notificationId, notification)

                    }

                    override fun onNotificationCancelled(
                        notificationId: Int,
                        dismissedByUser: Boolean
                    ) {
                        stopSelf()
                    }

                }
            )
            //attach player to playerNotificationManager
            playerNotificationManager.setPlayer(mPlayer)
        */
        }



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
            return START_STICKY
        }





        // concatenatingMediaSource to pass media as a list,
        // so that we can easily prev, next
        private fun getListOfMediaSource(): ConcatenatingMediaSource {
            val mediaUrlList = ArrayList<String>()
        //    mediaUrlList.add("http://streams.rpr1.de/rpr-metal-128-mp3?usid=0-0-H-M-D-45")
        //    mediaUrlList.add("https://storage.googleapis.com/exoplayer-test-media-0/Jazz_In_Paris.mp3")
        //    mediaUrlList.add("http://listen.radionomy.com/goth-n-metal.m3u")
        //    mediaUrlList.add("http://streams.deltaradio.de/delta-foehnfrisur/mp3-192/itunes/play.pls")

            mediaUrlList.add("https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8")
            mediaUrlList.add("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8")
            mediaUrlList.add("http://d3rlna7iyyu8wu.cloudfront.net/skip_armstrong/skip_armstrong_stereo_subs.m3u8")
            mediaUrlList.add("https://moctobpltc-i.akamaihd.net/hls/live/571329/eight/playlist.m3u8")
            mediaUrlList.add("https://multiplatform-f.akamaihd.net/i/multi/will/bunny/big_buck_bunny_,640x360_400,640x360_700,640x360_1000,950x540_1500,.f4v.csmil/master.m3u8")

            val concatenatingMediaSource = ConcatenatingMediaSource()
            for (mediaUrl in mediaUrlList) {
                concatenatingMediaSource.addMediaSource(buildMediaSource(mediaUrl))
            }

            return concatenatingMediaSource

        }





        //build media source to player
        private fun buildMediaSource(videoUrl: String): HlsMediaSource {
            val uri = Uri.parse(videoUrl)

            return HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
        }

        // detach player
        override fun onDestroy() {
            playerNotificationManager.hideNotification()

            mPlayer.release()
            super.onDestroy()
        }

        //removing service when user swipe out our app
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


    private MediaSessionCompat.Callback mediaSessionCallback = new MediaSessionCompat.Callback()
    {

        fun play(mediaItem: MediaItem) {

            val audioFocusResult = audioManager!!.requestAudioFocus(
                audioFocusChangeListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN
            )
            if (audioFocusResult != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) return

            // Аудиофокус надо получить строго до вызова setActive!
            mediaSession.isActive = true



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

            audioManager?.abandonAudioFocus(audioFocusChangeListener);

            Log.d(logTag, "stop")
        }
    }

    }