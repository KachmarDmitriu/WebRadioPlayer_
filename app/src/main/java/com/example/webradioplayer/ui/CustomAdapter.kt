   package com.example.webradioplayer.ui

import android.app.TaskStackBuilder.create
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.sqlite.db.SimpleSQLiteQuery.bind
import com.example.webradioplayer.R
import com.example.webradioplayer.dao.database.ListRadiostation
import com.example.webradioplayer.dao.database.WebPlayerDatabase
import kotlinx.coroutines.NonDisposableHandle.parent




   class CustomAdaper(val  items: List<String>) : RecyclerView.Adapter<CustomAdaper.ViewHolder>(WordsComparator()) {
//class CustomAdaper(val  items: List<String>):ListAdapter<ListRadiostation, CustomAdaper.ViewHolder>(ListRadiostationComparator()) {


       override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

           val view = LayoutInflater.from(parent.context)
               .inflate(R.layout.list_note_radiostation, parent, false)

           return ViewHolder(view)

       }

       override fun onBindViewHolder(holder: ViewHolder, position: Int) {

           val text = items[position]
           holder.textView.text = text

       }

       override fun getItemCount(): Int {
           return items.size
       }


       class ListRadiostationComparator  : DiffUtil.ItemCallback<ListRadiostation>() {
           override fun areItemsTheSame(
               oldItem: ListRadiostation,
               newItem: ListRadiostation
           ): Boolean {
               return oldItem === newItem
           }

           override fun areContentsTheSame(
               oldItem: ListRadiostation,
               newItem: ListRadiostation
           ): Boolean {

               return oldItem.uid == newItem.uid &&
                       oldItem.nameRadiostation == newItem.nameRadiostation &&
                       oldItem.genre == newItem.genre &&
                       oldItem.urlRadiostation == newItem.urlRadiostation
           }
       }



       inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
        {
            val textView: TextView

            init {
                textView = itemView as TextView
            }



           private val wordItemView: TextView = itemView.findViewById(R.id.textView)

           fun bind(text: String?) {
               wordItemView.text = text
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



   class WordsComparator : DiffUtil.ItemCallback<ListRadiostation>() {
       override fun areItemsTheSame(oldItem: ListRadiostation, newItem: ListRadiostation): Boolean {
           return oldItem === newItem
       }

       override fun areContentsTheSame(oldItem: ListRadiostation, newItem: ListRadiostation): Boolean {
           return oldItem.uid == newItem.uid &&
                   oldItem.nameRadiostation == newItem.nameRadiostation &&
                   oldItem.genre == newItem.genre &&
                   oldItem.urlRadiostation == newItem.urlRadiostation
       }



   companion object {
           private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<ListRadiostation>() {
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

   }



