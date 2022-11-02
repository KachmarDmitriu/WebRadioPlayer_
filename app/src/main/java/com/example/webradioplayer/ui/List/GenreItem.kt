package com.example.webradioplayer.ui.List

import android.view.View
import com.example.webradioplayer.R
import com.example.webradioplayer.databinding.GenreItemBinding
import com.example.webradioplayer.model.IGenre
import com.xwray.groupie.viewbinding.BindableItem

class GenreItem (
    private val model: IGenre,  // Country,
    private val onGenreClick: (IGenre) -> Unit) : BindableItem <GenreItemBinding>()
    {
        override fun bind(viewBinding: GenreItemBinding, position: Int) {
            viewBinding.genreItem = model
            viewBinding.root.setOnClickListener {
                onGenreClick.invoke(model)
            }
        }

        override fun getLayout() = R.layout.genre_item

        override fun initializeViewBinding(view: View) = GenreItemBinding.bind(view)
    }