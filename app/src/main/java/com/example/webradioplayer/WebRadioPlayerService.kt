package com.example.webradioplayer

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
    import android.content.Intent
    import android.graphics.Bitmap
    import android.net.Uri
    import android.os.IBinder
    import com.example.webradioplayer.PlayerActivity
    import com.example.webradioplayer.R
    import com.google.android.exoplayer2.Player
    import com.google.android.exoplayer2.SimpleExoPlayer
    import com.google.android.exoplayer2.source.ConcatenatingMediaSource
    import com.google.android.exoplayer2.source.MediaSource
    import com.google.android.exoplayer2.source.hls.HlsMediaSource
    import com.google.android.exoplayer2.ui.PlayerNotificationManager
    import com.google.android.exoplayer2.upstream.DataSource
    import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
    import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
    import com.google.android.exoplayer2.util.Util
import android.os.Binder





class PlayerNotificationService : Service()
{
        private lateinit var mPlayer: SimpleExoPlayer
        private lateinit var dataSourceFactory: DataSource.Factory
        private lateinit var playerNotificationManager: PlayerNotificationManager

        private var notificationId = 123;
        private var channelId = "channelId"


    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    // Binder given to clients
    private val binder = LocalBinder()

     /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): PlayerNotificationService = this@PlayerNotificationService //@LocalService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }


        override fun onCreate() {
            super.onCreate()
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
            playerNotificationManager.setPlayer(null)
            mPlayer.release()
            super.onDestroy()
        }

        //removing service when user swipe out our app
        override fun onTaskRemoved(rootIntent: Intent?) {
            super.onTaskRemoved(rootIntent)
            stopSelf()
        } 
    }