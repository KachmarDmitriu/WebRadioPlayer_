package com.example.webradioplayer.viewmodel

import androidx.lifecycle.*
import com.example.webradioplayer.DataRepository
import com.example.webradioplayer.database.entity.Playlist
import kotlinx.coroutines.launch

class PlaylistViewModel(private val repository: DataRepository): ViewModel() {

     fun insert(playlist: Playlist) = viewModelScope.launch {
        repository.addRadiostation(playlist)
    }

}


/*
class PlaylistViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlaylistViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlaylistViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/