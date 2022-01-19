package com.example.hw6architecture.common

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hw6architecture.movielist.Movie
import com.example.hw6architecture.movielist.MoviesListAdapter

abstract class BaseMovieListFragment : Fragment() {

    protected fun swapFavoriteFieldForMovie(movie: Movie): Movie {
        return  Movie(
            id = movie.id,
            title = movie.title,
            name = movie.name,
            overview = movie.overview,
            imageURi = movie.imageURi,
            backgroundURi = movie.backgroundURi,
            averageRating = movie.averageRating,
            popularity = movie.popularity,
            mediaType = movie.mediaType,
            page = movie.page,
            favorite = !movie.favorite,
        )
    }

    protected fun sorlListOfMovies(moviesList: List<Movie>): List<Movie> {
        val newList: MutableList<Movie> = mutableListOf()
        newList.addAll(moviesList)
        newList.sortByDescending { it.averageRating }
        return newList.toList()
    }
}