package com.example.hw6architecture.data

import android.content.ContentValues
import android.util.Log
import android.util.TimeUtils
import android.widget.TimePicker
import android.widget.Toast
import com.example.hw6architecture.PersistentApplication
import com.example.hw6architecture.data.network.*
import com.example.hw6architecture.immutable_values.Constants
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit

class ImdbRepository {

    private val imdbApi = NetworkBuilder.retrofitInit()

    private val saveTime = 0

    private val dataIsOutdated = (
        (saveTime + TimeUnit.HOURS.toMillis(6))
                < System.currentTimeMillis()
    )

    suspend fun getMovieList(): List<MoviesListItem> {

        var response: List<MoviesListItem> = emptyList()
        if (dataIsOutdated) {

            response = fetchMoviesList()
        } else {

            PersistentApplication.makeToast("GET CACHE", Toast.LENGTH_LONG)
        }
        return response
    }

    private suspend fun fetchMoviesList(): List<MoviesListItem> {
        var moviesList: List<MoviesListItem> = emptyList()
            try {
                moviesList = imdbApi.getMoviesList(
                    Constants.MOVIES_MEDIA_TYPE,
                    Constants.MOVIES_LIST_TIME_WINDOW
                ).results
                withContext(Dispatchers.Main) {
                    Log.e(ContentValues.TAG, "fetchMoviesList: ${ moviesList.size }", )
                }
            } catch(e: retrofit2.HttpException) {
                withContext(Dispatchers.Main) {
                    PersistentApplication.makeToast(e.toString(), Toast.LENGTH_LONG)

                }
            }
        return moviesList
    }

    suspend fun getMovieCast(mediaType: String,  movieId: Int): List<MovieActor> {

        var response: List<MovieActor> = emptyList()

        if (dataIsOutdated) {

            response = fetchMovieCast(mediaType, movieId)
        } else {

            PersistentApplication.makeToast("GET CACHE", Toast.LENGTH_LONG)
        }

        return response
    }


    private suspend fun fetchMovieCast(
        mediaType: String,
        movieId: Int
    ): List<MovieActor> {

        var result: List<MovieActor> = emptyList()

        try {

            result = imdbApi.getMovieCredits(mediaType, movieId).cast


        }
        catch(e: retrofit2.HttpException) {

            withContext(Dispatchers.Main) {
                PersistentApplication.makeToast(e.toString(), Toast.LENGTH_LONG)
            }
        }

        return result
    }
}