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
import kotlinx.coroutines.*

class MoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val job: CompletableJob = Job()

    private val repository = ImdbRepository(application)

//    val movies: MutableLiveData<List<MoviesListItem>> by lazy {
//
//        MutableLiveData<List<MoviesListItem>>()
//    }

    val _movies = MutableLiveData<List<MoviesListItem>>()
    var movies: LiveData<List<MoviesListItem>> = _movies

    init {
        viewModelScope.launch(Dispatchers.IO + job) {

            val movieslist = repository.getMovieList()

            withContext(Dispatchers.Main) {
                _movies.value = movieslist
            }

        }

    }


    suspend fun getActors(movieId: Int): List<MovieActor> {

        val mediaType = getMovieFromId(movieId)?.mediaType

        return if (mediaType != null) {

            repository.getMovieCast(mediaType, movieId)
        }
        else {
            PersistentApplication.makeToast("Can't find movie type", Toast.LENGTH_LONG)
            emptyList()
        }
    }

    fun getMovieFromId(movieId: Int): MoviesListItem? {

        return movies.value?.find { it.id == movieId }
    }
}