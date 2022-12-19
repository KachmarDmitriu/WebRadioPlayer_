package com.example.webradioplayer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.webradioplayer.R
import com.example.webradioplayer.databinding.GenreFragmentBinding
import com.example.webradioplayer.model.IGenre
import com.example.webradioplayer.ui.List.GenreItem
import com.example.webradioplayer.viewmodel.GenreViewModel
import com.example.webradioplayer.viewmodel.PlaylistViewModel
import com.xwray.groupie.GroupieAdapter
import timber.log.Timber

class GenreFragment: Fragment() {

    private lateinit var genreRecyclerView: RecyclerView

       private var mBinding: GenreFragmentBinding? = null

       private val binding get() = requireNotNull(mBinding)

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            val view = inflater.inflate(R.layout.genre_fragment, container, false)
                  //mBinding = GenreFragmentBinding.inflate(inflater, container, false)

            genreRecyclerView =
                view.findViewById(R.id.recycler_genre_list) as RecyclerView     //подключение рециклера к фрагменту
            //genreRecyclerView.layoutManager = LinearLayoutManager(context)

                    //return binding.root
            return view
        }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()

    }


    private val viewModel by viewModels<GenreViewModel>()
    private val playlistGenresViewModel by activityViewModels<PlaylistViewModel>()

    private val genresAdapter by lazy {
        return@lazy GroupieAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            init()
        }


    private fun init() {
        setupRefresh()
        setupRecycler()
      //  observeUiState()

        viewModel.loadGenre()
    }

    private fun setupRefresh() {
        binding.genreListLayout.setOnClickListener {
            viewModel.loadGenre()
        }
    }

    private fun setupRecycler() {
        binding.recyclerGenreList.adapter = genresAdapter  //recyclerCountries.adapter = countriesAdapter
    }
/*
            //что-то связаннное с обработкто ошибок,  пока ненадо
    private fun observeUiState() {
        viewModel.state.observe(viewLifecycleOwner, ::ErrLoadUiState)
    }

    private fun ErrLoadUiState(state: GenresUiState) {
        when (state) {
            is GenresLoadError -> onError(state.message)
            is GenresLoaded -> onGenresLoaded(state.data)
              is GenresLoading -> binding.swipeRefresh.isRefreshing = state.isLoading
        }
    }

*/
    private fun onError(message: String?) {
        Timber.e(message)
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun onGenresLoaded(genres: List<IGenre>) {
        genresAdapter.replaceAll(
            genres.map { genre ->
                GenreItem(genre, ::onGenreClick)
            }
        )
    }


    private fun onGenreClick(genre: IGenre) {
        playlistGenresViewModel.selectGenre(genre)
        //открытие фрагмента плейлиста по тыку на конкретном жанре
    }


}