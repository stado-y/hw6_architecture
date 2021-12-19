package com.example.hw6architecture.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw6architecture.data.ImdbRepository
import com.example.hw6architecture.movielist.Movie
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val movieId: Int): ViewModel() {

    private val repository = ImdbRepository.instance

    private val _actors = MutableLiveData<List<Actor>>()
    var actors: LiveData<List<Actor>> = _actors

    private val _chosenMovie = MutableLiveData<Movie>()
    var chosenMovie: LiveData<Movie> = _chosenMovie

    private val job: CompletableJob = Job()

    init {
        viewModelScope.launch(Dispatchers.IO + job) {
            _chosenMovie.postValue(getMovieFromId(movieId))

            _actors.postValue(getActors(movieId))
        }
    }

    override fun onCleared() {
        super.onCleared()

        if (job.isActive) {
            job.complete()
        }
    }


    private suspend fun getActors(movieId: Int): List<Actor> {

        val mediaType = getMovieFromId(movieId).mediaType

        return repository.getListOfActors(mediaType, movieId)
    }

    private suspend fun getMovieFromId(movieId: Int): Movie {

        return repository.getMovieFromDataBase(movieId)
    }
}