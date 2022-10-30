package com.example.webradioplayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.webradioplayer.database.PlaylistDatabase
import com.example.webradioplayer.database.dao.IGenreDao
import com.example.webradioplayer.database.entity.Genre
import com.example.webradioplayer.database.entity.Playlist


class DataRepository private constructor(private var mDatabase: PlaylistDatabase) {

    private val mObservableGenre: LiveData<List<Genre>>

   // val getGenre: LiveData<List<Genre>>
   //     get() = mObservableGenre          //для строки поиска

    //получение всей таблицы Genre
    fun loadGenre(): LiveData<List<Genre>> {
        return mDatabase.genreDao().getGenres()
    }

    //получение всей таблицы Playlist  \пока для теста, вся таблица\
    // fun loadPlaylist(genreId: Int): LiveData<List<Playlist>> {   использовать в рабочем приложении, с передачей ИД жанра
    fun loadPlaylist(): LiveData<List<Playlist>> {
        return mDatabase.playlistDao().getAllPlaylist()  //loadPlaylist(genreId) - по жанру, в рабочее приложение
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