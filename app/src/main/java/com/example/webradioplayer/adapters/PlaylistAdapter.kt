package com.example.webradioplayer.ui

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.webradioplayer.R
import com.example.webradioplayer.database.entity.Playlist


class PlaylistAdapter : ListAdapter<Playlist, PlaylistAdapter.PlaylistViewHolder>(PLAYLIST_COMPARATOR){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.nameRadiostation)  //что делать со вторым полем url ????
    }

    class PlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val  playlistItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?) {
           playlistItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): PlaylistViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return PlaylistViewHolder(view)
            }
        }
    }

    companion object {
        private val PLAYLIST_COMPARATOR = object : DiffUtil.ItemCallback<Playlist>() {
            override fun areItemsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
                return oldItem.nameRadiostation == newItem.nameRadiostation
                        && oldItem.urlRadiostation == newItem.urlRadiostation
            }
        }
    }


    //  код из BasicSample  -->  ProductAdapter

    var mPlaylistList: List<Playlist?>? = null

    fun setProductList(playlistList: List<Playlist?>) {
        if (mPlaylistList == null) {
            mPlaylistList = playlistList
            notifyItemRangeInserted(0, playlistList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return mPlaylistList.size
                }

                override fun getNewListSize(): Int {
                    return playlistList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return mPlaylistList.get(oldItemPosition).getId() ===
                            playlistList[newItemPosition].getId()
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    val newPlaylist: Playlist? = playlistList[newItemPosition]
                    val oldPlaylist: Playlist = mPlaylistList.get(oldItemPosition)
                    return (newPlaylist.getId() === oldPlaylist.getId() && TextUtils.equals(
                        newPlaylist.getDescription(),
                        oldPlaylist.getDescription()
                    )
                            && TextUtils.equals(newPlaylist.getName(), oldPlaylist.getName())
                            && newPlaylist.getPrice() === oldPlaylist.getPrice())
                }
            })
            mProductList = playlistList
            result.dispatchUpdatesTo(this)
        }
    }

}