package com.example.webradioplayer.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.webradioplayer.DataRepository
import com.example.webradioplayer.database.PlaylistDatabase
import com.example.webradioplayer.database.entity.Genre
import com.example.webradioplayer.database.entity.Playlist

//class GenreViewModel(private val repository: DataRepository): ViewModel() {
class DataRepository private constructor(private val mDatabase: PlaylistDatabase){

/*   fun insert(genre: Genre) = viewModelScope.launch{
       repository.addGenre(genre)
   }

  fun loadGenres(fetchFromRemote: Boolean = false) {
       viewModelScope.launch {
           repository
               .loadCountries(fetchFromRemote)
               .collect(::handleCountriesResource)
       }
   }
*/




   // с BasicSample

   fun loadGenre(genreId: Int): LiveData<Genre> {
       return mDatabase.genreDao().loadGenre(genreId)
   }

   fun loadPLaylist(genreId: Int): LiveData<Playlist> {
       return mDatabase.playlistDao().loadPlaylist(genreId)
   }


   private val mObservableGenre: LiveData<Genre>? = null

   private val mGenreId = 0

   private val mObservablePlaylist: LiveData<List<Playlist>>? = null

   fun ProductViewModel(
       application: Application, repository: DataRepository,
       productId: Int
   ) {
       super(application)
       mGenreId = productId
       mObservablePlaylist = repository.loadComments(mGenreId)
       mObservableGenre = repository.loadProduct(mGenreId)
   }

   private var mApplication: Application = null

   private var mProductId = 0

   private var mRepository: DataRepository? = null

   fun Factory(application: Application, productId: Int) {
       mApplication = application
       mGenreId = productId
       mRepository = (application as BasicApp).getRepository()
   }


   fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return com.example.android.persistence.viewmodel.ProductViewModel(
           mApplication,
           mRepository,
           mGenreId
       )
   }

}