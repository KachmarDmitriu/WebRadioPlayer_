package com.example.webradioplayer.model

import androidx.lifecycle.*
import com.example.webradioplayer.dao.database.Playlist
import kotlinx.coroutines.launch


class PlaylistViewModel(private val repository: PlaylistRepository): ViewModel() {

    val allPlaylist: LiveData<List<Playlist>> = repository.allPlaylist.asFlow().asLiveData()

    fun insert(playlist: Playlist) = viewModelScope.launch {
        repository.insert(playlist)
    }               // playlistDao.insert(playlist)
}


class PlaylistViewModelFactory(private val repository: PlaylistRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlaylistViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlaylistViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}