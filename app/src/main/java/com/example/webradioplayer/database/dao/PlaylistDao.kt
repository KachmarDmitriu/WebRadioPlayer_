package com.example.webradioplayer.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.webradioplayer.database.entity.Playlist

@Dao
interface PlaylistDao {

   @Query("SELECT * FROM playlist_table ORDER BY name_radiostation")
   fun getPlaylist(): LiveData<List<Playlist>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRadiostation(radiostation: Playlist)

}