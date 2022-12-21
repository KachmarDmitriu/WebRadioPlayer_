package com.example.webradioplayer

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.webradioplayer.ui.PlayerFragment
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


const val NOW_PLAYING_CHANNEL_ID = "com.example.webradioplayer.NOW_PLAYING"
const val NOW_PLAYING_NOTIFICATION_ID = 0x1111 // Arbitrary number used to identify our notification


class CustomPlayerNotificationManager(
    private val context: Context,
    notificationListener: PlayerNotificationManager.NotificationListener
) {

    private val notificationManager: PlayerNotificationManager = PlayerNotificationManager.Builder(
        context,
        NOW_PLAYING_NOTIFICATION_ID,
        NOW_PLAYING_CHANNEL_ID
    ).setChannelDescriptionResourceId(R.string.notification_channel_description)
        .setChannelNameResourceId(R.string.notification_channel)
        .setMediaDescriptionAdapter(DescriptionAdapter())
        .setNotificationListener(notificationListener)
        .build().apply {
            setSmallIcon(R.mipmap.ic_launcher_round)
        }

    fun hideNotification() {
        notificationManager.setPlayer(null)
    }

    fun showNotificationForPlayer(player: Player) {
        notificationManager.setPlayer(player)
    }

    private inner class DescriptionAdapter() :
        PlayerNotificationManager.MediaDescriptionAdapter {

        override fun createCurrentContentIntent(player: Player): PendingIntent? =
            PendingIntent.getActivity(
                context, 0, Intent(context, PlayerFragment::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        override fun getCurrentContentText(player: Player) =
            "Subtitle"

        override fun getCurrentContentTitle(player: Player) =
            "Title"

        override fun getCurrentLargeIcon(
            player: Player,
            callback: PlayerNotificationManager.BitmapCallback
        ) = null

        private suspend fun resolveUriAsBitmap(uri: Uri): Bitmap? {
            return withContext(Dispatchers.IO) {
                // Block on downloading artwork.
                try {
                    Glide.with(context).applyDefaultRequestOptions(glideOptions)
                        .asBitmap()
                        .load(uri)
                        .submit(NOTIFICATION_LARGE_ICON_SIZE, NOTIFICATION_LARGE_ICON_SIZE)
                        .get()
                } catch (e: Exception) {
                    Log.e("Glide", e.toString());
                    null
                }
            }
        }
    }
}

const val NOTIFICATION_LARGE_ICON_SIZE = 144 // px

private val glideOptions = RequestOptions()
    .fallback(R.mipmap.ic_launcher_round)
    .diskCacheStrategy(DiskCacheStrategy.DATA)