package com.example.hw6architecture

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hw6architecture.data.ImdbRepository
import com.example.hw6architecture.data.network.MovieActor
import com.example.hw6architecture.data.network.MoviesListItem
import com.example.hw6architecture.utils.Utils
import kotlinx.coroutines.*

class MoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ImdbRepository(application)

    private val _movies = MutableLiveData<List<MoviesListItem>>()
    var movies: LiveData<List<MoviesListItem>> = _movies

    init {
        viewModelScope.launch {
            _movies.value = repository.getMovieList()
        }
    }

    suspend fun getActors(movieId: Int): List<MovieActor> {
        val mediaType = getMovieFromId(movieId)?.mediaType
        return repository.getMovieCast(mediaType, movieId)
    }

    fun getMovieFromId(movieId: Int): MoviesListItem? =
        movies.value?.firstOrNull { it.id == movieId }
}