package com.example.webradioplayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.example.webradioplayer.database.PlaylistDatabase
import com.example.webradioplayer.database.entity.Genre
import com.example.webradioplayer.database.entity.Playlist


class DataRepository private constructor(private var mDatabase: PlaylistDatabase) {

    private var mObservableProducts: MediatorLiveData<List<Playlist>>

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    val products: MediatorLiveData<List<Playlist>>
        get() = mObservableProducts

    fun loadRadiostation(radiostationId: Int): LiveData<Playlist?>? {
        return mDatabase.playlistDao().loadProduct(radiostationId)
    }

    fun loadGenre(genreId: Int): LiveData<List<Genre?>?>? {
        return mDatabase.genreDao().loadGenre(genreId)
    }
/*

    fun searchRadiostation(query: String?): LiveData<List<Playlist>> {
        return mDatabase.playlistDao().searchAllProducts(query)
    }
*/
private var sInstance: DataRepository? = null



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
        mObservableProducts = MediatorLiveData()
        mObservableProducts.addSource(
            mDatabase.playlistDao().getAllPlaylist()
        ) { productEntities: List<Playlist> ->
            if (mDatabase.databaseCreated.value != null) {
                mObservableProducts.postValue(productEntities)
            }
        }
    }

}