package com.example.hw6architecture.movielist

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hw6architecture.data.network.GlideModuleImplementation.Companion.fillImageViewFromURI
import com.example.hw6architecture.databinding.MoviesListItemLayoutBinding
import com.example.hw6architecture.immutable_values.Constants
import com.example.hw6architecture.immutable_values.ImageSizes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


interface ItemClickListener {

    fun onItemClicked(movie: Movie)
}

class MoviesListAdapter(
    val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var moviesList = emptyList<Movie>()
        set(value) {

            value.map { Log.e(TAG, "${it.name}: ") }
            field = value
            notifyDataSetChanged()
        }

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
                movieListItemDescription.text = cropText(item.overview)

                val rating = (item.averageRating * 10).toInt()
                CoroutineScope(Dispatchers.Main).launch {
                    progressBarIndicator.setProgressCompat(
                        rating,
                        true
                    )
                }
                RatingTextView.text = Constants.RATING_FORMAT_TEMPLATE.format(rating)
            }
        }

        private fun cropText(text: String): String {

            return if (text.count() > Constants.MAX_OVERVIEW_LENGTH) {

                text.dropLast(text.count() - Constants.MAX_OVERVIEW_LENGTH)
                    .dropLastWhile { !it.isWhitespace() }
                    .plus("...")
            }
            else {
                text
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = MoviesListItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return MoviesListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MoviesListViewHolder) {
            holder.bind(moviesList[position])
        }
    }

    override fun getItemCount() = moviesList.size
}