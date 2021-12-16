package com.example.hw6architecture.movielist

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hw6architecture.data.network.GlideModuleImplementation.Companion.fillImageViewFromURI
import com.example.hw6architecture.data.network.MoviesListItem
import com.example.hw6architecture.databinding.MoviesListItemLayoutBinding
import com.example.hw6architecture.immutable_values.Constants
import com.example.hw6architecture.immutable_values.ImageSizes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


fun interface ItemClickListener {
    fun onItemClicked(movie: MoviesListItem)
}

class MoviesListAdapter(
        val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<MoviesListAdapter.MoviesListViewHolder>() {

    var moviesList = emptyList<MoviesListItem>()
        set(value) {
            value.map { Log.e(TAG, "${it.name}: ") }
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MoviesListAdapter.MoviesListViewHolder {
        val view = MoviesListItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
        )
        return MoviesListViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesListAdapter.MoviesListViewHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    override fun getItemCount() = moviesList.size

    inner class MoviesListViewHolder(
            private val binding: MoviesListItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MoviesListItem) {
            with(binding) {
                binding.root.setOnClickListener { itemClickListener.onItemClicked(item) }

                fillImageViewFromURI(
                        movieListItemImage,
                        item.imageURi,
                        ImageSizes.Poster.SMALL.size
                )

                movieListItemTitle.text = item.title ?: item.name
                movieListItemDescription.text = item.overview

                val rating = (item.averageRating * 10).toInt()

                progressBarIndicator.post {
                    progressBarIndicator.setProgressCompat(
                            rating, true
                    )
                }
                RatingTextView.text = Constants.RATING_FORMAT_TEMPLATE.format(rating)
            }
        }
    }
}