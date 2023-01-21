package com.example.webradioplayer

import android.app.Application
import com.example.webradioplayer.database.PlaylistDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber

class WebRadioPlayerApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { PlaylistDatabase.getDatabase(this, applicationScope) }

    override fun onCreate() {
        super.onCreate()
        DataRepository.getInstance(this)
        setupTimber()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}

