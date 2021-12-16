package com.example.hw6architecture.data

import android.app.Application
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import com.example.hw6architecture.data.local.MovieCreditsDao
import com.example.hw6architecture.data.local.MoviesListDao
import com.example.hw6architecture.data.local.MoviesRoom
import com.example.hw6architecture.data.network.*
import com.example.hw6architecture.immutable_values.Constants
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class ImdbRepository(private val application: Application) {

    private val imdbApi = NetworkBuilder.retrofitInit()

    private val room = MoviesRoom.getDatabase(application.applicationContext)

    private val moviesListDao: MoviesListDao = room.moviesListDao()
    private val movieCreditsDao: MovieCreditsDao = room.MovieCreditsDao()

    private val saveTime: Long = getTimeOfSave()




    suspend fun getMovieList(): List<MoviesListItem> = withContext(Dispatchers.IO) {

        var response: List<MoviesListItem> = emptyList()
        if (dataIsOutdated && isInternetConnected) {

            response = fetchMoviesList()
            moviesListDao.insertMovieList(response)
            saveCurrentTime()
        } else {
            response = moviesListDao.getMovieList()
            if (response.count() == 0 && !dataIsOutdated) {

                dataIsOutdated = true
                return@withContext getMovieList()
            }
            Log.e(TAG, "getMovieList: CACHED SIZE : ${response.count()}", )
            withContext(Dispatchers.Main) {

                makeToast("GET CACHE")
            }

        }
        return@withContext response
    }

    private suspend fun fetchMoviesList(): List<MoviesListItem> {
        var moviesList: List<MoviesListItem> = emptyList()
            try {
                moviesList = imdbApi.getMoviesList(
                    Constants.MOVIES_MEDIA_TYPE,
                    Constants.MOVIES_LIST_TIME_WINDOW
                ).results
                withContext(Dispatchers.Main) {
                    Log.e(TAG, "fetchMoviesList: ${ moviesList.size }", )
                }
            } catch(e: retrofit2.HttpException) {
                withContext(Dispatchers.Main) {
                    makeToast(e.toString())

                }
            }
        return moviesList
    }

    suspend fun getMovieCast(mediaType: String?,  movieId: Int): List<MovieActor> {
        mediaType ?: return emptyList()

        var response: List<MovieActor> = emptyList()

        if (dataIsOutdated && isInternetConnected) {

            val moviecast = fetchMovieCast(mediaType, movieId)
            if (moviecast != null) {
                movieCreditsDao.insertMovieCredits(moviecast)

                Log.e(TAG, "getMovieCast: insert MOVIECREDITS: ${moviecast.movieId}", )
            }
            moviecast?.let { response = it.cast }

        } else {
            response = movieCreditsDao.getActorsList(movieId)
            withContext(Dispatchers.Main) {

                makeToast("GET CACHE")
            }

        }

        return response
    }


    private suspend fun fetchMovieCast(
        mediaType: String,
        movieId: Int
    ): MovieCreditsResponse? {

        var result: MovieCreditsResponse? = null

        try {

            result = imdbApi.getMovieCredits(mediaType, movieId)


        }
        catch(e: retrofit2.HttpException) {

            withContext(Dispatchers.Main) {
                makeToast(e.toString())
            }
        }

        return result
    }




    fun getPreferences (): SharedPreferences {

        return application
            .applicationContext
            .getSharedPreferences(
                APPLICATION_PREFS,
                Application.MODE_PRIVATE,
            )
    }

    fun saveCurrentTime() {

        val editor = getPreferences().edit()
        editor.putLong(DATABASE_SAVE_TIME, System.currentTimeMillis())
        editor.apply()
    }

    fun getTimeOfSave(): Long {

        return getPreferences().getLong(DATABASE_SAVE_TIME, 0L)
    }

    private var dataIsOutdated = (
            (saveTime + TimeUnit.HOURS.toMillis(6))
                    < System.currentTimeMillis()
            )


    private val isInternetConnected: Boolean
        get() {
            return (application.applicationContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager)
                .activeNetworkInfo?.isConnected == true
        }

    private fun makeToast(text: String) {

        Toast.makeText(
            application.applicationContext,
            text,
            Toast.LENGTH_LONG
        ).show()
    }

    companion object {

        const val APPLICATION_PREFS = "hw6_preferences"

        const val DATABASE_SAVE_TIME = "database_save_time"
    }
}