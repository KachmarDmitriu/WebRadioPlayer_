package com.example.webradioplayer.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.webradioplayer.dao.models.ListRadiostation


@Database(entities = [ListRadiostation::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun radiostantionDao(): ListRadiostationDao
}