package com.example.webradioplayer.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.webradioplayer.database.entity.Genre
import com.example.webradioplayer.database.entity.Playlist

public interface GenreDao {

    @Query("SELECT * FROM genre_table ORDER BY genre_name")
    fun getGenres(): LiveData<List<Playlist>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenre(genre: Genre)

}