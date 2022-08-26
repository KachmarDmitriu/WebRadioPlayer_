package com.example.webradioplayer.dao.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlaylistDao {

   @Query("SELECT * FROM playlist_table GROUP BY genre ORDER BY name_radiostation")
   fun getAllListRadiostation(): LiveData<List<Playlist>?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(radiostation: Playlist)

    @Update
    fun update(radiostation: Playlist)

    @Delete
    fun delete(radiostation: Playlist)
}