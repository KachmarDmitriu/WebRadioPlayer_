package com.example.webradioplayer.database.entity

import androidx.room.*
import com.example.webradioplayer.model.IPlaylist

@Entity(
    tableName = "playlist_table",
    foreignKeys = [ForeignKey(
            entity = Genre::class,
            parentColumns = ["id_genre"],
            childColumns  = ["id_genre_pls"],
            onDelete = ForeignKey.CASCADE
        )],
    indices = [Index(value = ["id_genre_pls"])]
)

class Playlist : IPlaylist {

    @PrimaryKey    (autoGenerate = true)
    var id_playlist = 0
    var id_genre_pls = 0
    var name_radiostation: String? = null
    var url_radiostation: String? = null

    override var idPlaylist: Int
        get() = id_playlist
        set(value) {this.idPlaylist = value}

    override var idGenre: Int
        get() = id_genre_pls
        set(value) {this.idGenre = value}

    override var nameRadiostation: String?
        get() = name_radiostation
        set(value) {this.nameRadiostation = value}

    override var urlRadiostation: String?
        get() = url_radiostation
        set(value) {this.urlRadiostation = value}

    constructor() {}

    @Ignore
    constructor(id_playlist: Int, id_genre_pls: Int, name_radiostation: String?, url_radiostation: String?)
    {
        this.idPlaylist = id_playlist
        this.idGenre = id_genre_pls
        this.nameRadiostation = name_radiostation
        this.urlRadiostation = url_radiostation
    }

    constructor(playlist: IPlaylist) {
        id_playlist = playlist.idPlaylist
        id_genre_pls = playlist.idGenre
        name_radiostation = playlist.nameRadiostation
        url_radiostation = playlist.urlRadiostation
    }

}
