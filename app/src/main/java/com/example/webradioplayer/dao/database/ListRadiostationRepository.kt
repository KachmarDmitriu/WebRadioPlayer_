package com.example.webradioplayer.dao.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import java.util.concurrent.Flow

class ListRadiostationRepository (private val listRadiostatiomDao: ListRadiostationDao) {
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allWords: LiveData<List<ListRadiostation>> = listRadiostatiomDao.getAllListRadiostation()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(radiostation: ListRadiostation) {
        listRadiostatiomDao.insert(radiostation)
    }

}