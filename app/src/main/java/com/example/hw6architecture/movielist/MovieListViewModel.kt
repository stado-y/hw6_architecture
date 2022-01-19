package com.example.hw6architecture.movielist

import android.util.Log
import androidx.lifecycle.*
import com.example.hw6architecture.common.BaseViewModel
import com.example.hw6architecture.data.ImdbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: ImdbRepository
) : BaseViewModel() {

    val movies: Flow<List<Movie>> = repository.getMovieList()

    fun updateMovie(movie: Movie) {
        execute(request = { repository.updateMovieInDB(movie) })
    }

    override fun showError(error: Throwable?) {
        Log.e("MovieListViewModel", "showError: $error")
    }
}