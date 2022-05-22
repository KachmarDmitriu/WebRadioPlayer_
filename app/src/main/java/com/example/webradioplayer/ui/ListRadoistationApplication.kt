package com.example.webradioplayer.ui

import android.app.Application
import com.example.webradioplayer.dao.database.ListRadiostationRepository
import com.example.webradioplayer.dao.database.WebPlayerDatabase

class ListRadoistationApplication: Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { WebPlayerDatabase.getDatabase(this) }
    val repository by lazy { ListRadiostationRepository(database.radiostationDao()) }

}