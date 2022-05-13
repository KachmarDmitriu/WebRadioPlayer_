package com.example.webradioplayer.adapters

import com.example.webradioplayer.dao.database.ListRadiostation
import com.example.webradioplayer.model.NoteRadiostation

object NoteRadiostationMapper {

        fun map(noteListRadiostation: ListRadiostation): NoteRadiostation {
            val uid = noteListRadiostation.uid
            val name = noteListRadiostation.nameRadiostation
            val url = noteListRadiostation.urlRadiostation
            val genre = noteListRadiostation.genre

            return NoteRadiostation(
                uid,
                name,
                url,
                genre
            )
        }

        fun map(noteRadiostation: NoteRadiostation): ListRadiostation
        {
            val uid = noteRadiostation.uid
            val name = noteRadiostation.name
            val genre = noteRadiostation.genre
            val url = noteRadiostation.url

            return ListRadiostation(uid = uid, nameRadiostation = name, urlRadiostation = url, genre = genre)
        }

        fun mapNotes(noteEntityEntities: List<ListRadiostation>): List<NoteRadiostation> {
            return noteEntityEntities.map {
                map(it)
            }
        }

        fun mapNoteEntities(notes: List<NoteRadiostation>): List<ListRadiostation> {
            return notes.map {
                map(it)
            }
        }

    }