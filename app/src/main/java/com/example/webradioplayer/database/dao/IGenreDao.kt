package com.example.webradioplayer.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.webradioplayer.database.entity.Genre
import com.example.webradioplayer.database.entity.Playlist

public interface IGenreDao {

    @Query("SELECT * FROM genre_table ORDER BY genre_name")
    fun getGenres(): LiveData<List<Genre>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenre(genre: Genre)

  //  что долженвыводить ????  возможно,- во фрагменте  плейлиста выбранного жанра, выводит название текущего жанра
//  @Query("SELECT * FROM genre_table where id_genre = :genreId")
  //  fun loadGenre(genreId: Int): LiveData<Genre>
}