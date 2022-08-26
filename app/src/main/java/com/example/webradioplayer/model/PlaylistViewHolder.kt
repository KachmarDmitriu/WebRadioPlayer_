package com.example.webradioplayer.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.webradioplayer.R

internal class PlaylistViewHolder  private constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private val wordItemView: TextView
    fun bind(text: String?) {
        wordItemView.text = text
    }

    companion object {
        fun create(parent: ViewGroup): PlaylistViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false)
            return PlaylistViewHolder(view)
        }
    }

    init {
        wordItemView = itemView.findViewById(R.id.textView)
    }
}