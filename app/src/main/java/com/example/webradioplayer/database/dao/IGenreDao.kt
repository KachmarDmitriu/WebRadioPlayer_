package com.example.webradioplayer.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.webradioplayer.database.entity.Genre

@Dao
interface IGenreDao {

    @Query("SELECT * FROM genre_table ORDER BY genre_name")
    fun getGenres(): LiveData<List<Genre?>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenre(genre: Genre)

}