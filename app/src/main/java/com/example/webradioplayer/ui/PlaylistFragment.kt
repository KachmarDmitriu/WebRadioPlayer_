package com.example.webradioplayer.ui

import android.os.Bundle
import androidx.fragment.app.Fragment


class PlaylistFragment : Fragment() {


    /** Creates product fragment for specific product ID  */
    companion object {
        private const val KEY_PRODUCT_ID = "genre_id"

        /** Creates product fragment for specific product ID  */
        fun forGenre(genreId: Int): PlaylistFragment {
            val fragment = PlaylistFragment()
            val args = Bundle()
            args.putInt(KEY_PRODUCT_ID, genreId)
            fragment.arguments = args
            return fragment
        }
    }
}