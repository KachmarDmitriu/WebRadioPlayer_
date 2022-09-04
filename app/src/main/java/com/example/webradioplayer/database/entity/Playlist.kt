package com.example.webradioplayer.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity  (tableName = "playlist_table")
data class Playlist(

    @PrimaryKey

    @ColumnInfo(name = "url_radiostation")
    var urlRadiostation: String,

    @ColumnInfo(name = "name_radiostation")
    val nameRadiostation: String,
/*
    @ColumnInfo(name = "genre")
    var genre: String
 */

)
