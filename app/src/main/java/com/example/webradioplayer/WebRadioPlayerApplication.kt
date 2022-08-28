package com.example.webradioplayer

import android.app.Application
import com.example.webradioplayer.dao.database.PlaylistDatabase
import com.example.webradioplayer.model.PlaylistRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WebRadioPlayerApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { PlaylistDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { PlaylistRepository(database.playlistDao()) }
}

