package com.example.hw6architecture.data.local

import android.util.Log
import androidx.room.*
import com.example.hw6architecture.movielist.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Dao
interface MoviesDao {

    @Query("SELECT * FROM ${Movie.TABLE_NAME} WHERE page = :page")
    fun getMovieList(page: Int = 1): Flow<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    suspend fun insertMovieList(movieList: List<Movie>) {
        withContext(Dispatchers.IO) {
            for (movie in movieList) {
                movie.favorite = isMovieFavorite(movie.id) ?: false
                Log.e("MoviesDao", "insertMovieList: ${movie.id} : ${movie.favorite}", )
                insertMovie(movie)
            }
        }
    }

    @Query("SELECT favorite FROM ${Movie.TABLE_NAME} WHERE id = :movieId")
    fun isMovieFavorite(movieId: Int): Boolean

    @Query("SELECT * FROM ${Movie.TABLE_NAME} WHERE id = :movieId")
    fun getMovie(movieId: Int): Movie

    @Query("SELECT * FROM ${Movie.TABLE_NAME} WHERE favorite = 1")
    fun getFavoriteMovies(): Flow<List<Movie>>

    @Update(entity = Movie::class, onConflict = OnConflictStrategy.REPLACE)
    fun updateMovie(movie: Movie)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun updateMovie(movie: Movie)
}