package com.example.webradioplayer.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.webradioplayer.R
import com.example.webradioplayer.database.entity.Genre
import com.example.webradioplayer.databinding.GenreFragmentBinding
import com.example.webradioplayer.model.IGenre
import com.example.webradioplayer.ui.List.GenreItem
import com.example.webradioplayer.viewmodel.GenreViewModel
import com.example.webradioplayer.viewmodel.PlaylistViewModel
import com.xwray.groupie.GroupieAdapter
import timber.log.Timber
import java.util.*

private const val TAG = "GenreFragment"

class GenreFragment : Fragment() {

    private lateinit var genreRecyclerView: RecyclerView
    private var adapter: GenreAdapter? = GenreAdapter(emptyList())
    private val genreViewModel by viewModels<GenreViewModel>()



    private var mBinding: GenreFragmentBinding? = null
    private val binding get() = requireNotNull(mBinding)

    private val playlistGenresViewModel by activityViewModels<PlaylistViewModel>()

    private val genresAdapter by lazy {
        return@lazy GroupieAdapter()
    }

    interface Callbacks {
        fun onGenreSelected(genreId: UUID)
    }
    private var callbacks: Callbacks? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.genre_fragment, container, false)
        //mBinding = GenreFragmentBinding.inflate(inflater, container, false)

        genreRecyclerView =
            view.findViewById(R.id.recycler_genre_list) as RecyclerView     //подключение рециклера к фрагменту
        genreRecyclerView.layoutManager = LinearLayoutManager(context)
        genreRecyclerView.adapter = adapter

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)                       //  page 279
        genreViewModel.loadGenre().observe(
            viewLifecycleOwner,
            Observer { genres ->
                genres?.let {
                    Timber.tag(TAG).i("Got genres %s", genres.size)
                    updateUI(genres)
                }
            }
        )

       // init()
    }

    private fun updateUI(genres: List<Genre>) {
        adapter = GenreAdapter(genres)
        genreRecyclerView.adapter = adapter
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }





    private fun init() {
        //  setupRefresh()   для теста
        // setupRecycler()  для теста
        //  observeUiState()

        genreViewModel.loadGenre()
    }

    private fun setupRefresh() {
        binding.genreListLayout.setOnClickListener {
            genreViewModel.loadGenre()
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


    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()

    }



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






      //что-то связанное с редактированием (пример из книги)

    private inner class GenreHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var genreHld: Genre

        private val genreTextView: TextView = itemView.findViewById(R.id.TextViewGenre)
        //private val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        //private val solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(genre: Genre) {
            this.genreHld = genre
            genreTextView.text = this.genreHld.genre
            //dateTextView.text = this.crime.date.toString()

        /*
            solvedImageView.visibility = if (genre.isSolved) {

                View.VISIBLE
            } else {
                View.GONE
            }
        */

        }

        override fun onClick(v: View) {
            Toast.makeText(context, "${genreHld.genre} clicked!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private inner class GenreAdapter(var genres: List<Genre>)
        : RecyclerView.Adapter<GenreHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : GenreHolder {
            val view = layoutInflater.inflate(R.layout.genre_item, parent, false)
            return GenreHolder(view)
        }

        override fun onBindViewHolder(holder: GenreHolder, position: Int) {
            val genre = genres[position]
            holder.bind(genre)
        }

        override fun getItemCount() = genres.size
    }

    companion object {
        fun newInstance(): GenreFragment {
            return GenreFragment()
        }
    }


}