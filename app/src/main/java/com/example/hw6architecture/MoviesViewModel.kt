package com.example.hw6architecture

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hw6architecture.data.ImdbRepository
import com.example.hw6architecture.moviedetails.Actor
import com.example.hw6architecture.movielist.Movie
import com.example.hw6architecture.utils.ToastMaker
import kotlinx.coroutines.*

class MoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val job: CompletableJob = Job()


    private val repository = ImdbRepository.instance


    private val _movies = MutableLiveData<List<Movie>>()
    var movies: LiveData<List<Movie>> = _movies

    init {

        viewModelScope.launch(Dispatchers.IO + job) {
            val movielist = repository.getMovieList()
            withContext(Dispatchers.Main) {
                _movies.value = movielist
            }
        }
    }

    suspend fun getActors(movieId: Int): List<Actor> {

        val mediaType = getMovieFromId(movieId)?.mediaType

        return if (mediaType != null) {

            repository.getListOfActors(mediaType, movieId)
        }
        else {
            ToastMaker.instance.showToast("Can't find movie type")
            emptyList()
        }
    }

    fun getMovieFromId(movieId: Int): Movie? {

        return movies.value?.find { it.id == movieId }
    }
}