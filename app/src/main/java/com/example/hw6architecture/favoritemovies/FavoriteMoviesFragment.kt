package com.example.hw6architecture.favoritemovies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hw6architecture.Ext.observeWithLifecycle
import com.example.hw6architecture.R
import com.example.hw6architecture.common.BaseMovieListFragment
import com.example.hw6architecture.databinding.FragmentFavoriteMoviesBinding
import com.example.hw6architecture.movielist.ItemClickListener
import com.example.hw6architecture.movielist.Movie
import com.example.hw6architecture.movielist.MoviesListAdapter
import com.example.hw6architecture.movielist.MoviesListRecyclerDecorator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMoviesFragment : BaseMovieListFragment(), ItemClickListener {

    lateinit var binding: FragmentFavoriteMoviesBinding

    val viewModel: FavoriteMoviesViewModel by viewModels()

    private lateinit var moviesAdapter: MoviesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteMoviesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesAdapter = MoviesListAdapter(this)
        initRecycler()

        viewModel.favoriteMovies.observeWithLifecycle(viewLifecycleOwner) {
            updateAdapter(it)
        }
    }

    override fun onItemClicked(movie: Movie) {
        val action =
            FavoriteMoviesFragmentDirections.actionFavoriteMoviesFragmentToMovieDetailsFragment(
                movie.id
            )
        findNavController().navigate(action)
    }

    override fun onFavoriteClicked(movie: Movie) {
        viewModel.updateMovie(swapFavoriteFieldForMovie(movie))
    }

    private fun updateAdapter(moviesList: List<Movie>) {
        moviesAdapter.submitList(sorlListOfMovies(moviesList))
    }

    private fun initRecycler() {
        binding.favoriteMoviesRecycler.apply {
            layoutManager = GridLayoutManager(this.context, 2)
            adapter = moviesAdapter
            addItemDecoration(
                MoviesListRecyclerDecorator(
                    resources.getInteger(
                        R.integer.recycler_item_offset
                    )
                )
            )
        }
    }
}