package com.example.webradioplayer.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.webradioplayer.database.dao.IGenreDao
import com.example.webradioplayer.database.dao.IPlaylistDao
import com.example.webradioplayer.database.entity.Genre
import com.example.webradioplayer.database.entity.Playlist
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Playlist::class, Genre::class], version = 2)  //использование классов описывающих таблицы

abstract class PlaylistDatabase : RoomDatabase() {
    abstract fun playlistDao(): IPlaylistDao        //декларация классов для доступа к таблице наследующих интерфейс с описанием запросов к БД
    abstract fun genreDao(): IGenreDao


    private val mIsDatabaseCreated = MutableLiveData<Boolean>()

    private fun setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true)
    }

    val databaseCreated: LiveData<Boolean>
        get() = mIsDatabaseCreated



    companion object {
        @Volatile
        private var INSTANCE: PlaylistDatabase? = null

        fun getDatabase(context: Context, applicationScope: CoroutineScope): PlaylistDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    PlaylistDatabase::class.java,
                    "app_database")
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }


}