package com.example.webradioplayer.dao.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "playlist_table")
data class ListRadiostation(
    @PrimaryKey (autoGenerate = true)
    var uid: Int = 0,

    @ColumnInfo(name = "name_radiostation")
    var nameRadiostation: String,

    @ColumnInfo(name = "url_radiostation")
    var urlRadiostation: String,

    @ColumnInfo(name = "genre")
    var genre: String
)