package com.example.webradioplayer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.fragment.app.viewModels
import com.example.webradioplayer.R
import com.example.webradioplayer.adapters.GenreAdapter
import com.example.webradioplayer.database.entity.Genre
import com.example.webradioplayer.databinding.GenreFragmentBinding
import com.example.webradioplayer.model.IGenre
import com.example.webradioplayer.ui.Callback.IGenreClickCallback
import com.example.webradioplayer.ui.List.GenreItem
import com.example.webradioplayer.viewmodel.GenreViewModel
import com.example.webradioplayer.viewmodel.PlaylistViewModel
import com.xwray.groupie.GroupieAdapter     //сторонний адаптер
import timber.log.Timber            //логер


class GenreFragment: Fragment() {

  //  class ProductListFragment : Fragment() {
        private var mGenreAdapter: GenreAdapter? = null
        private var mBinding: GenreFragmentBinding? = null

       private val binding get() = requireNotNull(mBinding)



        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            mBinding = GenreFragmentBinding.inflate(inflater, container, false)// DataBindingUtil.inflate(inflater, R.layout.genre_fragment, container, false)
            // mGenreAdapter = GenreAdapter(mGenreClickCallback)

            //binding.genreList.adapter =  mGenreAdapter
            return binding.root
        }

    override fun onDestroyView() {
        mBinding = null
        mGenreAdapter = null
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
        observeUiState()

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

    private fun onError(message: String?) {
        Timber.e(message)
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun onGenresLoaded(genres: List<IGenre>) {
        genresAdapter.replaceAll(
            genres.map { genre ->
                GenreItem(genre, ::onGenreClick)        //реализовать  GenreItem (там просто скопировано CountyItem)
            }
        )
    }

    private fun onGenreClick(genre: IGenre) {
        playlistGenresViewModel      countryDetailsViewModel.selectCountry(country)
                                        //открытие фрагмента плейлиста по тыку на конкретном жанре
    }


    /*  для поиска в списке, пока не используется,
    val viewModel: ProductListViewModel =
        ViewModelProvider(this).get(ProductListViewModel::class.java)
    mBinding.productsSearchBtn.setOnClickListener { v ->
        val query: Editable = mBinding.productsSearchBox.getText()
        viewModel.setQuery(query)
    }
    subscribeUi(viewModel.getProducts())
}

private fun subscribeUi(liveData: LiveData<List<Genre>>) {
    // Update the list when the data changes
    liveData.observe(
        viewLifecycleOwner
    ) { myGenre: List<Genre>? ->
        if (myGenre != null) {
            mBinding.setIsLoading(false)
            mGenreAdapter.setProductList(myGenre)
        } else {
            mBinding.setIsLoading(true)
        }
        // espresso does not know how to wait for data binding's loop so we execute changes
        // sync.
        mBinding.executePendingBindings()
    }
}
*/




        private val mGenreClickCallback: IGenreClickCallback =
            IGenreClickCallback { genre ->
                if (lifecycle.currentState
                        .isAtLeast(Lifecycle.State.STARTED)
                ) {
                    (requireActivity() as PlayerActivity).show(genre)
                }
            }

        companion object {
            const val TAG = "ProductListFragment"
        }
}