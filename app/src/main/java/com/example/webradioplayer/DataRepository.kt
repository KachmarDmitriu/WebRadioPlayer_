package com.example.webradioplayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.webradioplayer.database.PlaylistDatabase
import com.example.webradioplayer.database.dao.IGenreDao
import com.example.webradioplayer.database.entity.Genre
import com.example.webradioplayer.database.entity.Playlist


class DataRepository private constructor(private var mDatabase: PlaylistDatabase) {

    private val mObservableGenre: MediatorLiveData<List<Genre>>

    val get_genre: LiveData<List<Genre>>
        get() = mObservableGenre

    fun loadGenre(genreId: Int): LiveData<Genre> {
        return mDatabase.genreDao().loadGenre(genreId)
    }

    fun loadPlaylist(playlistId: Int): LiveData<Playlist> {
        return mDatabase.playlistDao().loadPlaylist(playlistId)
    }

    suspend fun addGenre(genre: Genre)
    {
        mDatabase.genreDao().insertGenre(genre)
    }

    suspend fun addRadiostation(radiostation: Playlist)
    {
        mDatabase.playlistDao().insertRadiostation(radiostation)
    }
    /*

    fun searchRadiostation(query: String?): LiveData<List<Playlist>> {
        return mDatabase.playlistDao().searchAllProducts(query)
    }
*/




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