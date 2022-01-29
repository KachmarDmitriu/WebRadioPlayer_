package com.example.webradioplayer.dao.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ListRadiostation(
    @PrimaryKey (autoGenerate = true)
    val uid: Int = 0,
    val nameRadiostation: String,
    val urlRadiostation: String,
    val genre: String

)
