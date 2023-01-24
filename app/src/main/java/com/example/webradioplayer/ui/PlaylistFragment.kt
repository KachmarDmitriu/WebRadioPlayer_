package com.example.webradioplayer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.webradioplayer.database.entity.Genre
import com.example.webradioplayer.databinding.PlaylistFragmentBinding
import com.example.webradioplayer.model.IPlaylist
import com.example.webradioplayer.viewmodel.PlaylistViewModel
import com.xwray.groupie.GroupieAdapter
import timber.log.Timber
import java.util.*

private const val ARG_GENRE_ID = "genre_id"


class PlaylistFragment : Fragment() {

    private lateinit var genre: Genre



    private var mBinding: PlaylistFragmentBinding? = null
    private val binding get() = requireNotNull(mBinding)




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        genre = Genre()
        val genreId: UUID = arguments?.getSerializable(ARG_GENRE_ID) as UUID
        PlaylistViewModel. *****  crimeDetailViewModel.loadCrime(genreId)
    }





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
     //   observeUiState()


    }

    private fun setupRefresh() {
        binding.playlistListLayout.setOnClickListener {

        }
    }

    private fun setupRecycler() {
        binding.recyclerPlaylistList.adapter = playlistAdapter
    }
/*
 //что-то связаннное с обработкто ошибок,  пока ненадо
   private fun observeUiState() {
     //   viewModel.state.observe(viewLifecycleOwner, ::ErrLoadPlsUiState)
    }

    private fun ErrLoadPlsUiState(state: PlaylistUiState) {
        when (state) {
          //  is GenreSelected -> binding.country = state.country
        }
    }
*/

    private fun onError(message: String?) {
        Timber.e(message)
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun onPLaylistLoaded(playlist: List<IPlaylist>) {
     /*   playlistAdapter.replaceAll(
            playlist.map { pls ->
                PlaylistItem(pls, ::onPlaylistClick)
            }
        )*/
    }


    private fun onPlaylistClick(playlist: IPlaylist) {
       // playlistGenresViewModel.selectGenre(genre)

        //Вставить получение ссылки радиостанции и переклбючение на нее радио.
    }







    companion object {

        fun newInstance(genreId: UUID): PlaylistFragment {
            val args = Bundle().apply {
                putSerializable(ARG_GENRE_ID, genreId)
            }
            return  PlaylistFragment().apply {
                arguments = args
            }
        }
    }


}
