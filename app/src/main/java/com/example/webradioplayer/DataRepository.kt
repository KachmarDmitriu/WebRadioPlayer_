package com.example.webradioplayer

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.webradioplayer.database.entity.Playlist
import com.example.webradioplayer.database.dao.PlaylistDao

class DataRepository (private val playlistDao: PlaylistDao) {

       val allPlaylist: LiveData<List<Playlist>> = playlistDao.getPlaylist()

        @Suppress("RedundantSuspendModifier")
        @WorkerThread
        suspend fun insert(playlist: Playlist) {
            playlistDao.insertRadiostation(playlist)
        }


}