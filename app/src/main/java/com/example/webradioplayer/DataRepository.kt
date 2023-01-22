package com.example.webradioplayer

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import com.example.webradioplayer.database.PlaylistDatabase
import com.example.webradioplayer.database.dao.IPlaylistDao
import com.example.webradioplayer.database.entity.Genre
import com.example.webradioplayer.database.entity.Playlist
import java.lang.IllegalStateException
import java.util.*

private const val DATABASE_NAME = "app_database"

class DataRepository private constructor(context: Context){//private var mDatabase: PlaylistDatabase) {

    private val database : PlaylistDatabase =  Room.databaseBuilder(
        context.applicationContext,
        PlaylistDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val genreDao = database.genreDao()
    private val playlistDao = database.playlistDao()

    fun loadGenre(): LiveData<List<Genre?>> = genreDao.getGenres()
    fun loadPlaylist(genreId: Int): LiveData<List<Playlist?>> = playlistDao.loadPlaylist(genreId)




 //   private val mObservableGenre: LiveData<List<Genre>>

/*
     //получение всей таблицы Genre
    fun loadGenre(): LiveData<List<Genre>> {
        return mDatabase.genreDao().getGenres()
    }
*/

    //получение всей таблицы Playlist  \пока для теста, вся таблица\
/*
    fun loadPlaylist(): LiveData<List<Playlist>> {
        return mDatabase.playlistDao().getAllPlaylist()
    }

    //с передачей ИД жанра
    fun loadGenrePlaylist(genreId: Int): LiveData<List<Playlist>> {
        return mDatabase.playlistDao().loadPlaylist(genreId) //- по жанру, в рабочее приложение
    }
*/

    /*
    suspend fun addGenre(genre: Genre)
    {
        mDatabase.genreDao().insertGenre(genre)
    }

    suspend fun addRadiostation(radiostation: Playlist)
    {
        mDatabase.playlistDao().insertRadiostation(radiostation)
    }
*/



    companion object {
        private var sInstance: DataRepository? = null
        @JvmStatic
        fun getInstance(context: Context){ //} database: PlaylistDatabase): DataRepository? {
            if (sInstance == null) {
                // synchronized(DataRepository::class.java) {
                sInstance = DataRepository(context)

                }
            }

        fun get(): DataRepository{
            return sInstance ?:
            throw  IllegalStateException("Repository must be initialized")
        }

    }


/*
    init {
        mObservableGenre = MediatorLiveData()
        mObservableGenre.addSource(
            mDatabase.genreDao().getGenres()
        ) { genreEntities: List<Genre> ->
            if (mDatabase.databaseCreated.value != null) {
                mObservableGenre.postValue(genreEntities)
            }
        }
    }*/

}