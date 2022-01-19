package com.example.hw6architecture.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hw6architecture.moviedetails.MovieActorDomain

@Dao
interface MoviesJoinActorsDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieAndActorsJoin(join: MoviesAndActorsJoin)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieAndActorsJoin(join: ArrayList<MoviesAndActorsJoin>)

    @Query(
        "SELECT * FROM ${Actor.TABLE_NAME} JOIN ${MoviesAndActorsJoin.TABLE_NAME} " +
                "ON ${MoviesAndActorsJoin.TABLE_NAME}.actorName = ${Actor.TABLE_NAME}.name " +
                "WHERE ${MoviesAndActorsJoin.TABLE_NAME}.movieId = :idOfMovie"
    )
    fun getListOfActors(idOfMovie: Int): List<MovieActorDomain>
}