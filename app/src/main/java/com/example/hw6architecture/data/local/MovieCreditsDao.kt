package com.example.hw6architecture.data.local

import androidx.room.*
import com.example.hw6architecture.data.network.MovieActor
import com.example.hw6architecture.data.network.MovieCreditsResponse


@Dao
interface MovieCreditsDao {


    @Query("SELECT * FROM ${ MovieCreditsResponse.TABLE_NAME } where movieId = :id")
    fun getMovieCreditsResponse(id: Int): MovieCreditsResponse

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieCredits(credits: MovieCreditsResponse)

    @Update
    fun updateMovieCredits(movieCredits: MovieCreditsResponse)

    fun getActorsList(id: Int): List<MovieActor> {

        return getMovieCreditsResponse(id)?.cast
    }
}