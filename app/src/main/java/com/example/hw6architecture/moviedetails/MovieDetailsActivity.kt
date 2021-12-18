package com.example.hw6architecture.moviedetails

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hw6architecture.MoviesViewModel
import com.example.hw6architecture.Hw6Architecture
import com.example.hw6architecture.R
import com.example.hw6architecture.data.network.GlideModuleImplementation.Companion.fillImageViewFromURI
import com.example.hw6architecture.databinding.ActivityMovieDetailsBinding
import com.example.hw6architecture.immutable_values.Constants
import com.example.hw6architecture.immutable_values.ImageSizes
import com.example.hw6architecture.movielist.Movie
import com.example.hw6architecture.utils.ToastMaker
import kotlinx.coroutines.*
import kotlin.properties.Delegates

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding

    private val actorsAdapter = ActorsAdapter()

    private lateinit var viewModel: MoviesViewModel


    private var currentMovieId by Delegates.notNull<Int>()

    private lateinit var currentMovie: Movie

    private val job: CompletableJob = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = (application as Hw6Architecture).viewModel

        currentMovieId = getIntentMovieId()

        val findMovie = viewModel.getMovieFromId(currentMovieId)

        if (findMovie != null) {

            currentMovie = findMovie
        }
        else {
            ToastMaker.instance.showToast("can't find movie")

            val intent = Intent()
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
        initRecycler()
        fillViews()
        fillAdapter(currentMovieId)

    }

    private fun getIntentMovieId(): Int {

        return intent.getIntExtra(
            Constants.INTENT_MOVIE_ID_KEY,
            0
        )
    }

    private fun initRecycler() {
        binding.actorsRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                this.context,
                RecyclerView.HORIZONTAL,
                false
            )
            adapter = actorsAdapter
            addItemDecoration(ActorsRecyclerDecorator(resources.getInteger(R.integer.recycler_item_offset)))
        }
    }

    private fun fillAdapter(movieId: Int) {

        CoroutineScope(Dispatchers.IO + job).launch {

           val actors = viewModel.getActors(movieId)

            withContext(Dispatchers.Main) {
                updateAdapter(actors)
                setProgressBar()
            }
        }

    }

    override fun onStop() {
        super.onStop()
        Glide.with(this).clear(binding.backgroundImageView)
        Glide.with(this).clear(binding.movieImageView)
        if (job.isActive) { job.cancel() }
    }

    fun setProgressBar() {
        binding.progressBarForMovie.setProgressCompat(
            (currentMovie.averageRating * 10).toInt(),
            true)
    }

    private fun updateAdapter(actors: List<Actor>) {
        if (actors != null) {
            actors.apply {
                sortedByDescending { it.popularity }
                if (count() > 10) {
                    dropLast(count() - 10)
                }
            }
            actorsAdapter.actorsList = actors
        }
    }

    private fun fillViews() {

        with (binding) {

            movieNameTextView.text = currentMovie.title
            movieOverviewTexrView.text = currentMovie.overview
            ratingTextView.text = Constants.RATING_FORMAT_TEMPLATE.format((currentMovie.averageRating * 10).toInt())

            fillImageViewFromURI(
                backgroundImageView,
                currentMovie.backgroundURi,
                ImageSizes.Backdrop.MEDIUM.size,
            )

            fillImageViewFromURI(
                movieImageView,
                currentMovie.imageURi,
                ImageSizes.Poster.MEDIUM.size,
            )
        }
    }
}