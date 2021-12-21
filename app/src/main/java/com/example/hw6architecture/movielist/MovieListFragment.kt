package com.example.hw6architecture.movielist

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hw6architecture.R
import com.example.hw6architecture.databinding.FragmentMovieListBinding


class MovieListFragment : Fragment() {

    private lateinit var binding: FragmentMovieListBinding

    private lateinit var movieListAdapter: MoviesListAdapter

    private lateinit var viewModel: MovieListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieListAdapter = MoviesListAdapter((activity as ItemClickListener))

        viewModel = MovieListViewModel()

        initRecycler()

        viewModel.movies.observe(viewLifecycleOwner) {
            Log.e(ContentValues.TAG, "onCreate: OBSERVER CALL")
            updateAdapter(it)
        }
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
        }
    }

    private fun updateAdapter(moviesList: List<Movie>) {
        movieListAdapter.submitList(moviesList.sortedByDescending { it.averageRating })
    }
}