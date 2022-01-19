package com.example.hw6architecture.data

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.util.Log
import com.example.hw6architecture.data.local.ActorsDao
import com.example.hw6architecture.data.local.MoviesDao
import com.example.hw6architecture.data.local.MoviesJoinActorsDao
import com.example.hw6architecture.data.network.*
import com.example.hw6architecture.immutable_values.Constants
import com.example.hw6architecture.moviedetails.Actor
import com.example.hw6architecture.moviedetails.MovieActorDomain
import com.example.hw6architecture.movielist.Movie
import com.example.hw6architecture.utils.ToastMaker
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ImdbRepository @Inject constructor(
    private val application: Application,
    private val imdbApi: ImdbApiClient,
    private val moviesDao: MoviesDao,
    private val actorsDao: ActorsDao,
    private val moviesJoinActorsDao: MoviesJoinActorsDao,
    private val toastMaker: ToastMaker
) {

    private val saveTime: Long = getTimeOfSave()

    private var dataIsOutdated = (
        (saveTime + TimeUnit.HOURS.toMillis(6))
                < System.currentTimeMillis()
    )

    private val isInternetConnected: Boolean
        get() {
            return (application.applicationContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager)
                .activeNetworkInfo?.isConnected == true
        }

    init {
        if (isInternetConnected && dataIsOutdated) {
            CoroutineScope(Dispatchers.IO).launch {
                fetchMoviesList()
            }
        }
    }

    fun getMovieList(page: Int = 1): Flow<List<Movie>> {
        return moviesDao.getMovieList(page)
    }

    private suspend fun fetchMoviesList() {
        try {
            val movieList = imdbApi.getMoviesList(
                Constants.MOVIES_MEDIA_TYPE,
                Constants.MOVIES_LIST_TIME_WINDOW
            ).transformToListOfMovies()
            saveMovieList(movieList)

        } catch (e: retrofit2.HttpException) {
            withContext(Dispatchers.Main) {
                makeToast(e.toString())
            }
        }
    }

    private suspend fun saveMovieList(movieList: List<Movie>) {
        moviesDao.insertMovieList(movieList)
        saveCurrentTime()
    }

    suspend fun getListOfActors(mediaType: String, movieId: Int): List<MovieActorDomain> {

        val movieList = moviesJoinActorsDao.getListOfActors(movieId)
        return if (movieList.isNullOrEmpty()) {
            fetchMovieCast(mediaType, movieId) ?: emptyList<MovieActorDomain>()
        } else {
            movieList
        }

    }

    private suspend fun fetchMovieCast(
        mediaType: String,
        movieId: Int
    ): List<MovieActorDomain>? {
        return withContext(Dispatchers.IO) {
            if (isInternetConnected) {
                try {
                    val response =
                        imdbApi.getMovieCredits(mediaType, movieId)
                    saveActorsList(response)

                    Log.e(TAG, "fetchMovieCast: SHOULD RETURN RESPONSE",)
                    return@withContext response.transformToMovieActorDomain()
                } catch (e: retrofit2.HttpException) {
                    withContext(Dispatchers.Main) {
                        makeToast(e.toString())
                    }
                } catch (e: UnknownHostException) {
                    Log.e(TAG, "fetchMovieCast: ${e}",)
                } catch (e: Exception) {
                    Log.e(TAG, "fetchMovieCast: UNCATCHED EXCEPTION!!! : ${e.toString()}",)
                }
            }
            Log.e(TAG, "fetchMovieCast: RETURNING NULL",)
            return@withContext null
        }
    }

    private suspend fun saveActorsList(response: MovieCreditsResponse) {
        actorsDao.insertActorsList(response.transformToListOfActors())
        moviesJoinActorsDao.insertMovieAndActorsJoin(response.transformToListOfMoviesAndActorsJoin())
    }

    private fun getPreferences(): SharedPreferences {
        return application
            .applicationContext
            .getSharedPreferences(
                APPLICATION_PREFS,
                Application.MODE_PRIVATE,
            )
    }

    private fun saveCurrentTime() {
        val editor = getPreferences().edit()
        editor.putLong(DATABASE_SAVE_TIME, System.currentTimeMillis())
        editor.apply()
    }

    private fun getTimeOfSave(): Long {
        return getPreferences().getLong(DATABASE_SAVE_TIME, 0L)
    }

    private fun makeToast(text: String) {
        toastMaker.showToast(text)
    }

    suspend fun getMovieFromDataBase(movieId: Int): Movie {
        return withContext(Dispatchers.IO) {
             moviesDao.getMovie(movieId)
        }
    }

    fun updateMovieInDB(movie: Movie) {
        moviesDao.updateMovie(movie)
    }

    fun getFavoriteMoviesFromDB(): Flow<List<Movie>> {
        return moviesDao.getFavoriteMovies()
    }

    companion object {

        const val APPLICATION_PREFS = "hw6_preferences"

        const val DATABASE_SAVE_TIME = "database_save_time"
    }
}