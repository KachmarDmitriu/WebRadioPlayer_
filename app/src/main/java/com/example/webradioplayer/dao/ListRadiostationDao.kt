package com.example.webradioplayer.dao

import androidx.room.*
import com.example.webradioplayer.dao.models.ListRadiostation


@Dao
interface ListRadiostationDao {
    @Query("SELECT uid, nameRadioStation, genre FROM listradiostation" +
            " GROUP BY nameRadioStation, genre")
    suspend fun getAllListRadiostation(): List<ListRadiostation>

    @Query("SELECT urlRadiostation FROM listradiostation WHERE uid = :uid")
    suspend fun getUrlLinkRadiostation(uid: Int): List<ListRadiostation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(listRadiostation: ListRadiostation)

    @Update
    fun update(listRadiostation: ListRadiostation)

    @Delete
    suspend fun delete(listRadiostation: ListRadiostation)
}