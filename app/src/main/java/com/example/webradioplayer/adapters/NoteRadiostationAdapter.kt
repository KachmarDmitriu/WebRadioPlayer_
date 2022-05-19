package com.example.webradioplayer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.webradioplayer.R
import com.example.webradioplayer.model.NoteRadiostation
import java.lang.String

class NoteRadiostationAdapter(context: Context?) : BaseAdapter() {
        private val notes: MutableList<NoteRadiostation>
        private val layoutInflater: LayoutInflater
        private var listener: OnItemClickListener? = null
    /*   fun addNoteToList(note: NoteRadiostation) {
            println(
                String.format(
                    "addNoteToList: %s, %s, %s",
                    note.getTitle(),
                    note.getDescription(),
                    note.getDate().toString()
                )
            )
            notes.add(note)
            notifyDataSetChanged()
        }
*/
        fun clear() {
            notes.clear()
            notifyDataSetChanged()
        }

        fun addAll(notes: List<NoteRadiostation>?) {
            this.notes.addAll(notes!!)
            notifyDataSetChanged()
        }

        override fun getCount(): Int {
            return notes.size
        }

        override fun getItem(i: Int): Any {
            return notes[i]
        }

        override fun getItemId(i: Int): Long {
            return notes[i].uid.toLong()
        }

        override fun getView(i: Int, view: View, viewGroup: ViewGroup): View {
            var view = view
            view = layoutInflater.inflate(R.layout.list_note_radiostation, null)
            val root = view.findViewById<LinearLayout>(R.id.root)
            val uid = view.findViewById<TextView>(R.id.uid)
            val name = view.findViewById<TextView>(R.id.name)
            val url = view.findViewById<TextView>(R.id.url)
            val genre = view.findViewById<TextView>(R.id.genre)
            val note: NoteRadiostation = notes[i]
            uid.setText(note.uid)
            name.setText(note.name)
            genre.setText(note.genre)
            url.setText(note.url)
            root.setOnClickListener { view1: View? ->
                if (listener != null) {
                    listener!!.onItemClick(view1, note)
                }
            }
            return view
        }

        fun setOnItemClickListener(listener: OnItemClickListener?) {
            this.listener = listener
        }

        interface OnItemClickListener {
            fun onItemClick(view: View?, note: NoteRadiostation?)
        }

        init {
            println("created NotesAdapter")
            layoutInflater = LayoutInflater.from(context)
            notes = ArrayList<NoteRadiostation>()
        }
    }