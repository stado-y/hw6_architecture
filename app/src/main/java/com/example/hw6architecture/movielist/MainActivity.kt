package com.example.hw6architecture.movielist

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hw6architecture.MoviesViewModel
import com.example.hw6architecture.Hw6Architecture
import com.example.hw6architecture.R
import com.example.hw6architecture.databinding.ActivityMainBinding
import com.example.hw6architecture.immutable_values.Constants
import com.example.hw6architecture.moviedetails.MovieDetailsActivity

class MainActivity : AppCompatActivity(), ItemClickListener {

    private lateinit var binding: ActivityMainBinding

    private val movieListAdapter = MoviesListAdapter(this)

    private lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = (application as Hw6Architecture).viewModel

        initRecycler()

        viewModel.movies.observe(this) {

            Log.e(TAG, "onCreate: OBSERVER CALL", )

            updateAdapter(it)
        }

    }
    private fun initRecycler() {
        binding.movieListRecycler.apply {
            layoutManager = GridLayoutManager(this.context,2)
            adapter = movieListAdapter
            addItemDecoration(
                MoviesListRecyclerDecorator(resources.getInteger(
                R.integer.recycler_item_offset))
            )
        }
    }



    private fun updateAdapter(moviesList: List<Movie>) {

        movieListAdapter.moviesList = moviesList?.sortedByDescending { it.averageRating }
    }


    override fun onItemClicked(movie: Movie) {
        val intent = Intent(this, MovieDetailsActivity::class.java).apply {

            putExtra(Constants.INTENT_MOVIE_ID_KEY, movie.id)
            //putExtra(Constants.INTENT_MEDIA_TYPE_KEY, movie.mediaType)
        }
        startActivityForResult(intent, 1)
    }
}