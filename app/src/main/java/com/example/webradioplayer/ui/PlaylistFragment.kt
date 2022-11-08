package com.example.webradioplayer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.webradioplayer.databinding.PlaylistFragmentBinding
import com.example.webradioplayer.model.IGenre
import com.example.webradioplayer.model.IPlaylist
import com.example.webradioplayer.ui.List.GenreItem
import com.example.webradioplayer.ui.List.PlaylistItem
import com.example.webradioplayer.viewmodel.PlaylistViewModel
import com.xwray.groupie.GroupieAdapter
import timber.log.Timber


class PlaylistFragment : Fragment() {


    private var mBinding: PlaylistFragmentBinding? = null

    private val binding get() = requireNotNull(mBinding)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = PlaylistFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()

    }


    private val viewModel by viewModels<PlaylistViewModel>()
    private val playlistGenresViewModel by activityViewModels<PlaylistViewModel>()

    private val playlistAdapter by lazy {
        return@lazy GroupieAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }


    private fun init() {
        setupRefresh()
        setupRecycler()
        observeUiState()


    }

    private fun setupRefresh() {
        binding.playlistListLayout.setOnClickListener {

        }
    }

    private fun setupRecycler() {
        binding.recyclerPlaylistList.adapter = playlistAdapter
    }

    private fun observeUiState() {
        viewModel.state.observe(viewLifecycleOwner, ::ErrLoadPlsUiState)
    }

    private fun ErrLoadPlsUiState(state: PlaylistUiState) {
        when (state) {
            is PlaylistLoadError -> onError(state.message)
            is PlaylistLoaded -> onPLaylistLoaded(state.data)
            is PlaylistLoading -> binding.swipeRefresh.isRefreshing = state.isLoading
        }
    }

    private fun onError(message: String?) {
        Timber.e(message)
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun onPLaylistLoaded(playlist: List<IPlaylist>) {
        playlistAdapter.replaceAll(
            playlist.map { pls ->
                PlaylistItem(pls, ::onPlaylistClick)
            }
        )
    }


    private fun onPlaylistClick(playlist: IPlaylist) {
       // playlistGenresViewModel.selectGenre(genre)

        //Вставить получение ссылки радиостанции и переклбючение на нее радио.
    }









    /** Creates product fragment for specific product ID  */
    companion object {
        private const val KEY_GENRE_ID = "genre_id"

        /** Creates product fragment for specific product ID  */
        fun forGenre(genreId: Int): PlaylistFragment {
            val fragment = PlaylistFragment()
            val args = Bundle()
            args.putInt(KEY_GENRE_ID, genreId)
            fragment.arguments = args
            return fragment
        }
    }


}