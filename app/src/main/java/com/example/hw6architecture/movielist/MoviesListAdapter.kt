package com.example.hw6architecture.movielist

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hw6architecture.data.network.GlideModuleImplementation.Companion.fillImageViewFromURI
import com.example.hw6architecture.databinding.MoviesListItemLayoutBinding
import com.example.hw6architecture.immutable_values.Constants
import com.example.hw6architecture.immutable_values.ImageSizes
import com.example.hw6architecture.moviedetails.Actor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


interface ItemClickListener {

    fun onItemClicked(movie: Movie)
}

class MoviesListAdapter
    (
    val itemClickListener: ItemClickListener
) : ListAdapter<Movie,
        MoviesListAdapter.MoviesListViewHolder>(MovieListDiffUtil) {


    inner class MoviesListViewHolder(
        private val binding: MoviesListItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie) {
            with(binding) {
                root.setOnClickListener {
                    itemClickListener.onItemClicked(item)
                }

                fillImageViewFromURI(
                    movieListItemImage,
                    item.imageURi,
                    ImageSizes.Poster.SMALL.size
                )

                movieListItemTitle.text = item.title ?: item.name
                movieListItemDescription.text = item.overview//cropText(item.overview)

                val rating = (item.averageRating * 10).toInt()
                progressBarIndicator.post {
                    progressBarIndicator.setProgressCompat(
                        rating,
                        true
                    )
                }
                RatingTextView.text = Constants.RATING_FORMAT_TEMPLATE.format(rating)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        val view = MoviesListItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return MoviesListViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    companion object {
        private val MovieListDiffUtil = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}