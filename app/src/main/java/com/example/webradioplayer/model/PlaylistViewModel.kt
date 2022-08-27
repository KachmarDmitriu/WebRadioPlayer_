package com.example.webradioplayer.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.webradioplayer.dao.database.Playlist
import com.example.webradioplayer.dao.database.PlaylistDao

class PlaylistViewModel(private val playlistDao: PlaylistDao): ViewModel() {

    val allWords: LiveData<List<Playlist>?>? = playlistDao.getPlaylist()

    fun insert(playlist: Playlist) = playlistDao.insert(playlist)
}


class PlaylistViewModelFactory(private val playlistDao: PlaylistDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlaylistViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlaylistViewModel(playlistDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}