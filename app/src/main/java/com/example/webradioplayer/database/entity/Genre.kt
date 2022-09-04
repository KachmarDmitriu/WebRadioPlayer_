package com.example.webradioplayer.database.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.webradioplayer.model.IGenre
import com.example.webradioplayer.model.IPlaylist

@Entity(tableName = "genre_table")
class Genre: IGenre {

        @PrimaryKey
        var id_genre = 0
        var genre_name : String? = null

        constructor() {}

        @Ignore
        constructor(id: Int, genre_name: String?)
        {
            this.id_genre = id
            this.genre = genre_name
        }

        constructor(genre: IGenre) {
            id_genre = genre.id
            genre_name = genre.genre
        }

}