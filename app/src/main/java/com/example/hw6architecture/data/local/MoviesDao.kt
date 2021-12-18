package com.example.hw6architecture.data.local

import androidx.room.*
import com.example.hw6architecture.movielist.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Dao
interface MoviesDao {

    @Query("SELECT * FROM ${Movie.TABLE_NAME} WHERE page = :page")
    fun getMovieList(page: Int = 1): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    suspend fun insertMovieList(movieList: List<Movie>) {
        withContext(Dispatchers.IO) {
            for (movie in movieList) {
                insertMovie(movie)
            }
        }
    }
}