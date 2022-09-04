package com.example.webradioplayer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.webradioplayer.database.dao.GenreDao
import com.example.webradioplayer.database.dao.PlaylistDao
import com.example.webradioplayer.database.entity.Genre
import com.example.webradioplayer.database.entity.Playlist
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Playlist::class, Genre::class], version = 2)

abstract class PlaylistDatabase : RoomDatabase() {
    abstract fun playlistDao(): PlaylistDao
    abstract fun genreDao(): GenreDao



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