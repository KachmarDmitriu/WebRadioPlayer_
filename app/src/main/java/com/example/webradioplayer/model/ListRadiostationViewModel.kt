package com.example.webradioplayer.model

import androidx.lifecycle.*
import com.example.webradioplayer.dao.database.ListRadiostation
import com.example.webradioplayer.dao.database.ListRadiostationRepository
import kotlinx.coroutines.launch


class ListRadiostationViewModel {

    class WordViewModel(private val repository: ListRadiostationRepository) : ViewModel() {

        // Using LiveData and caching what allWords returns has several benefits:
        // - We can put an observer on the data (instead of polling for changes) and only update the
        //   the UI when the data actually changes.
        // - Repository is completely separated from the UI through the ViewModel.
        val allWords: LiveData<List<ListRadiostation>> = repository.allWords.asFlow().asLiveData() //??? repository.allWords.asLiveData()

        /**
         * Launching a new coroutine to insert the data in a non-blocking way
         */
        fun insert(listRadiostation: ListRadiostation ) = viewModelScope.launch {
            repository.insert(listRadiostation)
        }
    }

    class WordViewModelFactory(private val repository: ListRadiostationRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return WordViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}