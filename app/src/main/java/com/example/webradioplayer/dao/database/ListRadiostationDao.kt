package com.example.webradioplayer.dao.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ListRadiostationDao {

   @Query("SELECT * FROM playlist_table GROUP BY genre ORDER BY name_radiostation")
   fun getAllListRadiostation(): LiveData<List<ListRadiostation>?>?

  @Query("SELECT url_radiostation FROM playlist_table WHERE uid = :uid")
  fun getUrlLinkRadiostation(uid: Int):URL_for_MediaItem    //класс для получения ссылки на радио

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(radiostation: ListRadiostation)

    @Update
    fun update(radiostation: ListRadiostation)

    @Delete
    fun delete(radiostation: ListRadiostation)
}