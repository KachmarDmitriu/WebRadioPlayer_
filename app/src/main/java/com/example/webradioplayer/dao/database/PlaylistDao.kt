package com.example.webradioplayer.dao.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlaylistDao {

   @Query("SELECT * FROM playlist_table ORDER BY name_radiostation")
   fun getPlaylist(): LiveData<List<Playlist>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(radiostation: Playlist)

}