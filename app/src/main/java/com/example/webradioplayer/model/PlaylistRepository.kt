package com.example.webradioplayer.model

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.webradioplayer.dao.database.Playlist
import com.example.webradioplayer.dao.database.PlaylistDao
import kotlinx.coroutines.flow.Flow

class PlaylistRepository (private val playlistDao: PlaylistDao) {

       val allPlaylist: LiveData<List<Playlist>> = playlistDao.getPlaylist()

        @Suppress("RedundantSuspendModifier")
        @WorkerThread
        suspend fun insert(playlist: Playlist) {
            playlistDao.insert(playlist)
        }


}