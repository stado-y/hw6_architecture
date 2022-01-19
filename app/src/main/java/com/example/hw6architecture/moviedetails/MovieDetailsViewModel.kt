package com.example.hw6architecture.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw6architecture.common.BaseViewModel
import com.example.hw6architecture.data.ImdbRepository
import com.example.hw6architecture.movielist.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: ImdbRepository
) : BaseViewModel() {

    private val _actors = MutableLiveData<List<MovieActorDomain>>()
    var actors: LiveData<List<MovieActorDomain>> = _actors

    private val _chosenMovie = MutableLiveData<Movie>()
    var chosenMovie: LiveData<Movie> = _chosenMovie

    private val job: CompletableJob = Job()

    var movieId: Int = 0
        set(value) {
            field = value
            fillLivedata(value)
        }

    override fun onCleared() {
        super.onCleared()
        if (job.isActive) {
            job.complete()
        }
    }

    private fun fillLivedata(movieId: Int) {
        execute(request = {
            _chosenMovie.postValue(getMovieFromId(movieId))
            _actors.postValue(getActors(movieId))
        })
    }

    private suspend fun getActors(movieId: Int): List<MovieActorDomain> {
        val mediaType = getMovieFromId(movieId).mediaType
        return repository.getListOfActors(mediaType, movieId)
    }

    private suspend fun getMovieFromId(movieId: Int): Movie {
        return repository.getMovieFromDataBase(movieId)
    }
}