package com.example.webradioplayer.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.webradioplayer.R

class CustomAdaper(val  items: List<String>) : RecyclerView.Adapter<CustomAdaper.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val textView : TextView

        init {
            textView = view as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdaper.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_note_radiostation, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomAdaper.ViewHolder, position: Int) {

        val text = items[position]
        holder.textView.text = text

    }

    override fun getItemCount(): Int {
        return items.size
    }
}