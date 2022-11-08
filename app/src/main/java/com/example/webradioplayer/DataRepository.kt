package com.example.webradioplayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.webradioplayer.database.PlaylistDatabase
import com.example.webradioplayer.database.entity.Genre
import com.example.webradioplayer.database.entity.Playlist


class DataRepository private constructor(private var mDatabase: PlaylistDatabase) {

    private val mObservableGenre: LiveData<List<Genre>>

     //получение всей таблицы Genre
    fun loadGenre(): LiveData<List<Genre>> {
        return mDatabase.genreDao().getGenres()
    }

    //получение всей таблицы Playlist  \пока для теста, вся таблица\
    fun loadPlaylist(): LiveData<List<Playlist>> {
        return mDatabase.playlistDao().getAllPlaylist()
    }

    //с передачей ИД жанра
    fun loadGenrePlaylist(genreId: Int): LiveData<List<Playlist>> {
        return mDatabase.playlistDao().loadPlaylist(genreId) //- по жанру, в рабочее приложение
    }

    suspend fun addGenre(genre: Genre)
    {
        mDatabase.genreDao().insertGenre(genre)
    }

    suspend fun addRadiostation(radiostation: Playlist)
    {
        mDatabase.playlistDao().insertRadiostation(radiostation)
    }



    companion object {
        private var sInstance: DataRepository? = null
        @JvmStatic
        fun getInstance(database: PlaylistDatabase): DataRepository? {
            if (sInstance == null) {
                synchronized(DataRepository::class.java) {
                    if (sInstance == null) {
                        sInstance = DataRepository(database)
                    }
                }
            }
            return sInstance
        }
    }

    init {
        mObservableGenre = MediatorLiveData()
        mObservableGenre.addSource(
            mDatabase.genreDao().getGenres()
        ) { genreEntities: List<Genre> ->
            if (mDatabase.databaseCreated.value != null) {
                mObservableGenre.postValue(genreEntities)
            }
        }
    }

}