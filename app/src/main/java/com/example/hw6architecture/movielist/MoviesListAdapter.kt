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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


interface ItemClickListener {

    fun onItemClicked(movie: MoviesListItem)
}

class MoviesListAdapter(
    val mItemClickListener: ItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var moviesList = emptyList<MoviesListItem>()
        set(value) {

            value.map { Log.e(TAG, "${ it.name }: ", ) }
            field = value
            notifyDataSetChanged()
        }


    inner class MoviesListViewHolder(
        private val binding: MoviesListItemLayoutBinding
        ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MoviesListItem) {

            with (binding) {

                setInterfaceListener(
                    item,
                    movieListItemDescription,
                    movieListItemTitle,
                    movieListItemImage,
                )

                fillImageViewFromURI(
                    movieListItemImage,
                    item.imageURi,
                    ImageSizes.Poster.SMALL.size
                )

                movieListItemTitle.text = item.title ?: item.name
                movieListItemDescription.text = cropText(item.overview)

                val rating = (item.averageRating * 10).toInt()
                CoroutineScope(Dispatchers.Main).launch { progressBarIndicator.setProgressCompat(rating, true) }
                RatingTextView.text = Constants.RATING_FORMAT_TEMPLATE.format(rating)
            }



        }

        private fun setInterfaceListener(item: MoviesListItem, vararg views: View) {

            views.map {
                it.setOnClickListener {
                    mItemClickListener.onItemClicked(item)
                }
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