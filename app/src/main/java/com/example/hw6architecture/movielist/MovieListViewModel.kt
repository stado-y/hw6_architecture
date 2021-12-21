package com.example.hw6architecture.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw6architecture.data.ImdbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieListViewModel: ViewModel() {

    private val repository = ImdbRepository.instance

    private val _movies = MutableLiveData<List<Movie>>()
    var movies: LiveData<List<Movie>> = _movies

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _movies.postValue(repository.getMovieList())
        }
    }
}