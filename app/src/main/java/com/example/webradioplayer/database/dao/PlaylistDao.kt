package com.example.webradioplayer.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.webradioplayer.database.entity.Playlist

@Dao
interface PlaylistDao {

   @Query("SELECT * FROM playlist_table ORDER BY name_radiostation")
   fun getAllPlaylist(): LiveData<List<Playlist>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRadiostation(radiostation: Playlist)

    @Query("select * from playlist_table where id_playlist = :radiostationId")
    fun loadProduct(radiostationId: Int): LiveData<Playlist?>?
}