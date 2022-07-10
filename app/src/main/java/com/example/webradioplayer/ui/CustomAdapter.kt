   package com.example.webradioplayer.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.webradioplayer.R
import com.example.webradioplayer.dao.database.ListRadiostation

   class CustomAdaper(val  items: List<String>) : RecyclerView.Adapter<CustomAdaper.ViewHolder>()
   {
       override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

           val view = LayoutInflater.from(parent.context)
               .inflate(R.layout.list_note_radiostation, parent, false)

           return ViewHolder(view)
       }

       override fun onBindViewHolder(holder: ViewHolder, position: Int) {

           val item = items[position]
           holder.bind(item)

       }

       override fun getItemCount(): Int {
           return items.size
       }


       class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
       {

           private val listRadiostationItemView: TextView = itemView.findViewById(R.id.name)

       fun bind(text: String) {
           listRadiostationItemView.text = text.toString()
       }

       companion object {
           fun create(parent: ViewGroup): ViewHolder
           {
               val view: View = LayoutInflater.from(parent.context)
                   .inflate(R.layout.list_note_radiostation, parent, false)
               return ViewHolder(view)
           }
       }
   }

   companion object {
           private val LISTRADIO_COMPARATOR = object : DiffUtil.ItemCallback<ListRadiostation>() {
               override fun areItemsTheSame(oldItem: ListRadiostation, newItem: ListRadiostation): Boolean {
                   return oldItem === newItem
               }

               override fun areContentsTheSame(oldItem: ListRadiostation, newItem: ListRadiostation): Boolean {
                   return oldItem.uid == newItem.uid &&
                           oldItem.nameRadiostation == newItem.nameRadiostation &&
                           oldItem.genre == newItem.genre &&
                           oldItem.urlRadiostation == newItem.urlRadiostation
               }
           }
       }

   }
