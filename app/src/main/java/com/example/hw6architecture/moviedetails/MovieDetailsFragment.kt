package com.example.hw6architecture.moviedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hw6architecture.R
import com.example.hw6architecture.data.network.GlideModuleImplementation
import com.example.hw6architecture.databinding.FragmentMovieDetailsBinding
import com.example.hw6architecture.immutable_values.Constants
import com.example.hw6architecture.immutable_values.ImageSizes
import com.example.hw6architecture.movielist.Movie

class MovieDetailsFragment(private val movieId: Int) : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding

    private lateinit var viewModel: MovieDetailsViewModel

    private val actorsAdapter = ActorsAdapter()

    private lateinit var currentMovie: Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = MovieDetailsViewModel(movieId)

        viewModel.chosenMovie.observe(viewLifecycleOwner) {
            currentMovie = it
            setProgressBar()
            initRecycler()
            fillViews()
        }

        viewModel.actors.observe(viewLifecycleOwner) {
            updateAdapter(it)
        }
    }

    override fun onDetach() {
        super.onDetach()
        Glide.with(this).clear(binding.backgroundImageView)
        Glide.with(this).clear(binding.movieImageView)
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

    private fun fillViews() {
        with(binding) {
            movieNameTextView.text = currentMovie.title
            movieOverviewTexrView.text = currentMovie.overview
            ratingTextView.text =
                Constants.RATING_FORMAT_TEMPLATE.format((currentMovie.averageRating * 10).toInt())

            GlideModuleImplementation.fillImageViewFromURI(
                backgroundImageView,
                currentMovie.backgroundURi,
                ImageSizes.Backdrop.MEDIUM.size,
            )

            GlideModuleImplementation.fillImageViewFromURI(
                movieImageView,
                currentMovie.imageURi,
                ImageSizes.Poster.MEDIUM.size,
            )
        }
    }

    private fun setProgressBar() {
        binding.progressBarForMovie.post {
            binding.progressBarForMovie.setProgressCompat(
                (currentMovie.averageRating * 10).toInt(),
                true
            )
        }
    }

    private fun updateAdapter(actors: List<Actor>) {
        actorsAdapter.submitList(actors.sortedBy { it.order })
    }
}