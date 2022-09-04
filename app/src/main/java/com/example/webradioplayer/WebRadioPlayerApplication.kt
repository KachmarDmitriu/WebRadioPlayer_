package com.example.webradioplayer

import android.app.Application
import com.example.webradioplayer.database.PlaylistDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WebRadioPlayerApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { PlaylistDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { DataRepository(database.playlistDao()) }
}

