package com.example.webradioplayer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.webradioplayer.DataRepository
import com.example.webradioplayer.database.entity.Genre
import kotlinx.coroutines.launch

class GenreViewModel(private val repository: DataRepository): ViewModel() {

    fun insert(genre: Genre) = viewModelScope.launch{
        repository.addGenre(genre)
    }

}