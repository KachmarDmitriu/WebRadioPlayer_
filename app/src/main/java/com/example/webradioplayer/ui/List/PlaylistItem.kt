package com.example.webradioplayer.ui.List

import android.view.View
import com.example.webradioplayer.R
import com.example.webradioplayer.databinding.PlaylistItemBinding
import com.example.webradioplayer.model.IPlaylist
import com.xwray.groupie.viewbinding.BindableItem

class PlaylistItem (
    private val modelPl: IPlaylist,
    private val onPlaylistClick: (IPlaylist) -> Unit) : BindableItem<PlaylistItemBinding>()
{
    override fun bind(viewBinding: PlaylistItemBinding, position: Int) {
        viewBinding.playlistItem = modelPl
        viewBinding.root.setOnClickListener {
            onPlaylistClick.invoke(modelPl)
        }
    }

    override fun getLayout() = R.layout.playlist_item

    override fun initializeViewBinding(view: View) = PlaylistItemBinding.bind(view)
}