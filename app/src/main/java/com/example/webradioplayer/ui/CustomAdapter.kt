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
   //class CustomAdaper(val  items: List<String>) : RecyclerView.Adapter<CustomAdaper.ViewHolder>()//LISTRADIO_COMPARATOR)
   {
//class CustomAdaper(val  items: List<String>):ListAdapter<ListRadiostation, CustomAdaper.ViewHolder>(ListRadiostationComparator()) {

       override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

          // val view = LayoutInflater.from(parent.context)
            //   .inflate(R.layout.list_note_radiostation, parent, false)

          // return ViewHolder(view)

            return ViewHolder.create(parent)
       }

       override fun onBindViewHolder(holder: ViewHolder, position: Int) {

           val current = (position)
           holder.bind(current)  //textView.text = text

       }

       override fun getItemCount(): Int {
           return items.size
       }


       class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
       {
         //  val textView: TextView

         //  init {
         //      textView = itemView as TextView
         //  }

           private val listRadiostationItemView: TextView = itemView.findViewById(R.id.name)//textView)

       fun bind(text: Int) {
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


/*
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

*/

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

  // }



