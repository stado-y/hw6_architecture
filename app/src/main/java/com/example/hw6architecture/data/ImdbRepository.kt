package com.example.hw6architecture.data

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.util.Log
import com.example.hw6architecture.Hw6Architecture
import com.example.hw6architecture.data.local.ActorsDao
import com.example.hw6architecture.data.local.MoviesDao
import com.example.hw6architecture.data.local.MoviesRoom
import com.example.hw6architecture.data.network.*
import com.example.hw6architecture.immutable_values.Constants
import com.example.hw6architecture.moviedetails.Actor
import com.example.hw6architecture.movielist.Movie
import com.example.hw6architecture.utils.ToastMaker
import kotlinx.coroutines.*
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class ImdbRepository(private val application: Application) {

    private val imdbApi = NetworkBuilder.retrofitInit()

    private val room = MoviesRoom.getDatabase(application)

    private val moviesDao: MoviesDao = room.moviesDao()
    private val actorsDao: ActorsDao = room.actorsDao()

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

    suspend fun getMovieList(page: Int = 1): List<Movie> {
        Log.e(TAG, "getMovieList: dataIsOutdated : ${dataIsOutdated}", )
        Log.e(TAG, "getMovieList: isInternetConnected : ${isInternetConnected}", )
        return if (dataIsOutdated && isInternetConnected) {
            val response: List<Movie>? = fetchMoviesList()
            Log.e(TAG, "getMovieList: RESPONSE : ${response?.count() ?: response}")
            response ?: moviesDao.getMovieList(page)
        }
        else {
            withContext(Dispatchers.Main) {
                makeToast("GET CACHE")
            }

            moviesDao.getMovieList(page)
        }
    }

    private suspend fun fetchMoviesList(): List<Movie>? {
        try {
            val movieList = imdbApi.getMoviesList(
                Constants.MOVIES_MEDIA_TYPE,
                Constants.MOVIES_LIST_TIME_WINDOW
            ).transformToListOfMovies()
            saveMovieList(movieList)
            return movieList
        } catch (e: retrofit2.HttpException) {
            withContext(Dispatchers.Main) {
                makeToast(e.toString())
            }
        }
        return null
    }

    private suspend fun saveMovieList(movieList: List<Movie>) {
        moviesDao.insertMovieList(movieList)
        saveCurrentTime()
    }

    suspend fun getListOfActors(mediaType: String, movieId: Int): List<Actor> {
        val actors: List<Actor>? = actorsDao.getActorsList(movieId)
        Log.e(TAG, "getListOfActors: ACTORS : ${actors}", )
        return if (actors.isNullOrEmpty()) {
            Log.e(TAG, "getListOfActors: ACTORS IS NULL OR EMPTY", )
            fetchMovieCast(mediaType, movieId) ?: listOf<Actor>()
        }
        else {
            actors
        }
    }

    private suspend fun fetchMovieCast(
        mediaType: String,
        movieId: Int
    ): List<Actor>? {
        return withContext(Dispatchers.IO) {
            if (isInternetConnected) {
                try {
                    val response =
                        imdbApi.getMovieCredits(mediaType, movieId).transformToListOfActors()
                    saveActorsList(response)
                    Log.e(TAG, "fetchMovieCast: SHOULD RETURN RESPONSE",)
                    return@withContext response
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

    private suspend fun saveActorsList(actorsList: List<Actor>) {
        actorsDao.insertActorsList(actorsList)
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
        ToastMaker.instance.showToast(text)
    }

    suspend fun getMovieFromDataBase(movieId: Int): Movie {
        return withContext(Dispatchers.IO) {
             moviesDao.getMovie(movieId)
        }
    }

    companion object {

        const val APPLICATION_PREFS = "hw6_preferences"

        const val DATABASE_SAVE_TIME = "database_save_time"

        lateinit var instance: ImdbRepository
            private set

        fun create(application: Application) {
            instance = ImdbRepository(application)
        }

    }
}