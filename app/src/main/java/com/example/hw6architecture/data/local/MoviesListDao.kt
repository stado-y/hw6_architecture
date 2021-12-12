package com.example.hw6architecture.data.local

import androidx.room.*
import com.example.hw6architecture.data.network.MoviesListItem

@Dao
interface MoviesListDao {

    @Query("SELECT * FROM ${ MoviesListItem.TABLE_NAME }")
    fun getMovieList(): List<MoviesListItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieList(movieList: List<MoviesListItem>)

    @Update
    fun updateMovieList(movieList: List<MoviesListItem>)




}