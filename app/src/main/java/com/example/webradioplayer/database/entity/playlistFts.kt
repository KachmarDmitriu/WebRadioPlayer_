package com.example.webradioplayer.database.entity

import androidx.room.Entity
import androidx.room.Fts4

class playlistFts {

    @Entity(tableName = "playlistFts_table")
    @Fts4(contentEntity =  playlistFts::class)
    class ProductFtsEntity(val name_radiostation_Fts: String, val url_radiostation_Fts: String)

}