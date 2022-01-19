package com.example.hw6architecture.movielist

import androidx.lifecycle.*
import com.example.hw6architecture.data.ImdbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: ImdbRepository,
): ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    var movies: LiveData<List<Movie>> = _movies

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _movies.postValue(repository.getMovieList())
        }
    }
}