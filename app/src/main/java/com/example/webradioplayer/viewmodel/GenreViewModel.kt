package com.example.webradioplayer.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.webradioplayer.DataRepository
import com.example.webradioplayer.database.entity.Genre
import com.example.webradioplayer.database.entity.Playlist
import com.example.webradioplayer.ui.GenresUiState

class GenreViewModel(private val repository: DataRepository): ViewModel() {

   private val _state: MutableLiveData<GenresUiState> = MutableLiveData()
   val state: LiveData<GenresUiState> = _state

   fun loadGenre(): LiveData<List<Genre>> = repository.loadGenre()
       //return mDatabase.genreDao().loadGenre(genreId)  //возврат значения по определенному жанру
          //возврат всей таблицы *жанр*


}