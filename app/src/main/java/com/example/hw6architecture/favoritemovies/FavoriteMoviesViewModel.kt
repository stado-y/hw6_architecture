package com.example.hw6architecture.favoritemovies

import android.util.Log
import com.example.hw6architecture.common.BaseViewModel
import com.example.hw6architecture.data.ImdbRepository
import com.example.hw6architecture.movielist.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(
    private val repository: ImdbRepository
) : BaseViewModel() {

    val favoriteMovies: Flow<List<Movie>>  = repository.getFavoriteMoviesFromDB()

    fun updateMovie(movie: Movie) {
        execute(request = { repository.updateMovieInDB(movie) } )
    }

    override fun showError(error: Throwable?) {
        Log.e("FavoriteMoviesViewModel", "showError: $error")
    }
}