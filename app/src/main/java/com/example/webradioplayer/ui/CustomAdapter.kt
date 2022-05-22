   package com.example.webradioplayer.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.webradioplayer.R
import com.example.webradioplayer.dao.database.ListRadiostation

   class CustomAdaper(val  items: List<String>) : RecyclerView.Adapter<CustomAdaper.ViewHolder>() {
//class CustomAdaper(val  items: List<String>):ListAdapter<ListRadiostation, CustomAdaper.ViewHolder>(ListRadiostationComparator()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdaper.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_note_radiostation, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomAdaper.ViewHolder, position: Int) {

        val text =  items[position]
        holder.textView.text = text

    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
           val textView : TextView

           init {
               textView = view as TextView
           }
       }


       // Не до конца реализовано    Android Room with a View - Kotlin  Step 12

       class ListRadiostationComparator: DiffUtil.ItemCallback<ListRadiostation>() {
           override fun areItemsTheSame(oldItem: ListRadiostation, newItem: ListRadiostation): Boolean {
               return oldItem === newItem
           }

           override fun areContentsTheSame(oldItem: ListRadiostation, newItem: ListRadiostation): Boolean {
        //       oldItem.urlRadiostation == newItem.urlRadiostation
        //       oldItem.nameRadiostation == newItem.nameRadiostation
        //       oldItem.urlRadiostation == newItem.urlRadiostation
        //       oldItem.uid == newItem.uid
        //       return   ?????
               return  oldItem.uid == newItem.uid
           }
       }
}