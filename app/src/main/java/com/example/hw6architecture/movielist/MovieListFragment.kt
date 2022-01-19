package com.example.hw6architecture.movielist

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hw6architecture.Ext.observeWithLifecycle
import com.example.hw6architecture.R
import com.example.hw6architecture.common.BaseMovieListFragment
import com.example.hw6architecture.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : BaseMovieListFragment(), ItemClickListener {

    private val viewModel: MovieListViewModel by viewModels()

    private lateinit var binding: FragmentMovieListBinding

    private lateinit var movieListAdapter: MoviesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieListAdapter = MoviesListAdapter(this)

        initRecycler()

        viewModel.movies.observeWithLifecycle(viewLifecycleOwner) {
            Log.e(ContentValues.TAG, "onCreate: OBSERVER CALL")
            updateAdapter(it)
        }

        binding.floatingFavoriteMoviesButton.setOnClickListener {
            favoriteButtonClicked()
        }
    }

    private fun favoriteButtonClicked() {
        val action = MovieListFragmentDirections.actionMovieListFragmentToFavoriteMoviesFragment()
        findNavController().navigate(action)
    }

    private fun initRecycler() {
        binding.movieListRecycler.apply {
            layoutManager = GridLayoutManager(this.context, 2)
            adapter = movieListAdapter
            addItemDecoration(
                MoviesListRecyclerDecorator(
                    resources.getInteger(
                        R.integer.recycler_item_offset
                    )
                )
            )
            itemAnimator = null
        }
    }

    private fun updateAdapter(moviesList: List<Movie>) {
        movieListAdapter.submitList(sorlListOfMovies(moviesList))
    }

    override fun onItemClicked(movie: Movie) {
        val action =
            MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(movie.id)
        findNavController().navigate(action)
    }

    override fun onFavoriteClicked(movie: Movie) {
        viewModel.updateMovie(swapFavoriteFieldForMovie(movie))
    }
}