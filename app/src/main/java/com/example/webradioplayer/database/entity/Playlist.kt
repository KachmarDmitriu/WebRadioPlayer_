package com.example.webradioplayer.database.entity

import androidx.room.*
import com.example.webradioplayer.model.IPlaylist
import java.util.*

@Entity(
    tableName = "playlist_table",
    foreignKeys = [ForeignKey(
            entity = Genre::class,
            parentColumns = "id_genre",
            childColumns  = "id_genre_pls",
            onDelete = ForeignKey.CASCADE
        )],
    indices = [Index(value = "id_genre_pls")]
)

class Playlist : IPlaylist {

    @PrimaryKey    (autoGenerate = true)
    var id_playlist = 0
    var id_genre_pls = 0
    var name_radiostation: String? = null
    var url_radiostation: String? = null

    fun getidPlaylist(): Int {
        return id_playlist
    }

    fun setidPlaylist(id: Int) {
        this.idPlaylist = id_playlist
    }

    fun getidGenre(): Int {
        return id_genre_pls
    }

    fun setidGenre(productId: Int) {
        this.idGenre = id_genre_pls
    }

    fun getnameRadiostation(): String {
        return name_radiostation!!
    }

    fun setnameRadiostation(name_radiostation: String?) {
        this.nameRadiostation = name_radiostation
    }

    fun geturlRadiostation(): String {
        return url_radiostation!!
    }

    fun seturlRadiostation(name_radiostation: String?) {
        this.urlRadiostation = url_radiostation
    }

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
