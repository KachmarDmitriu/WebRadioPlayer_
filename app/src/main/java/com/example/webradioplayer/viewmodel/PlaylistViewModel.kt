package com.example.webradioplayer.viewmodel

import androidx.lifecycle.*
import com.example.webradioplayer.DataRepository
import com.example.webradioplayer.database.entity.Playlist
import com.example.webradioplayer.model.IGenre
import com.example.webradioplayer.ui.GenreSelected
import com.example.webradioplayer.ui.GenresUiState
import kotlinx.coroutines.launch

class PlaylistViewModel(private val repository: DataRepository): ViewModel() {

    fun loadPLaylist(): LiveData<List<Playlist>> {
        return repository.loadPlaylist()
    }


    // Для работы реализовать передачу  ИД жанра выбора
    //fun loadPLaylist(genreId: Int): LiveData<List<Playlist>> {
    //    return repository.loadPlaylist(genreId)
    //}


    fun insert(playlist: Playlist) = viewModelScope.launch {
        repository.addRadiostation(playlist)
    }


    private val _state: MutableLiveData<GenresUiState> = MutableLiveData()
    val state: LiveData<GenresUiState> = _state

    fun selectCountry(genre: IGenre) {
        _state.value = GenreSelected(genre)
    }


}