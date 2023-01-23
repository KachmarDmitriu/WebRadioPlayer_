package com.example.webradioplayer.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.webradioplayer.database.entity.Playlist

@Dao
interface IPlaylistDao {

    //получение всего плейлиста __ДЛЯ ТЕСТА_
   @Query("SELECT * FROM playlist_table ORDER BY name_radiostation")
   fun getAllPlaylist(): LiveData<List<Playlist>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRadiostation(radiostation: Playlist)

// получение плейлиста определенного жанра
    @Query("SELECT * FROM playlist_table WHERE id_genre_pls =(:genreId)")
    fun loadPlaylist(genreId: Int): LiveData<List<Playlist>>
}