package com.example.webradioplayer.ui

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.fragment.app.viewModels
import com.example.webradioplayer.R
import com.example.webradioplayer.adapters.GenreAdapter
import com.example.webradioplayer.database.entity.Genre
import com.example.webradioplayer.databinding.GenreFragmentBinding
import com.example.webradioplayer.ui.Callback.IGenreClickCallback
import com.example.webradioplayer.viewmodel.GenreViewModel


class GenreFragment: Fragment() {

  //  class ProductListFragment : Fragment() {
        private var mGenreAdapter: GenreAdapter? = null
        private var mBinding: GenreFragmentBinding? = null

    private val viewModel by viewModels<GenreViewModel>()

    private val binding get() = requireNotNull(mBinding)  // ?? что делает?? из проекта Артура

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            mBinding = DataBindingUtil.inflate(inflater, R.layout.genre_fragment, container, false)
            mGenreAdapter = GenreAdapter(mGenreClickCallback)

            binding.genreList.adapter =  mGenreAdapter
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            init()
        }


    private fun init() {
        setupRefresh()
        setupRecycler()
        observeUiState()

        viewModel.loadGenres()
    }

    private fun setupRefresh() {
        binding.genreListLayout.setOnClickListener {
            viewModel.loadCountries(true)
        }
    }

    private fun setupRecycler() {
        binding.recyclerCountries.adapter = countriesAdapter
    }

    private fun observeUiState() {
        viewModel.state.observe(viewLifecycleOwner, ::handleUiState)
    }

    private fun handleUiState(state: CountriesUiState) {
        when (state) {
            is CountriesLoadError -> onError(state.message)
            is CountriesLoaded -> onCountriesLoaded(state.data)
            is CountriesLoading -> binding.swipeRefresh.isRefreshing = state.isLoading
        }
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


        override fun onDestroyView() {
            mBinding = null
            mGenreAdapter = null
            super.onDestroyView()
        }

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