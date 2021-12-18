package com.example.hw6architecture.data.network

import androidx.room.PrimaryKey
import com.example.hw6architecture.movielist.Movie
import com.google.gson.annotations.SerializedName

data class MoviesListResponse(
    val page: Int,
    val results: List<MoviesListItem>,
    val total_pages: Int,
    val total_results: Int,
) {
    fun transformToListOfMovies(): List<Movie> {
        val movies: MutableList<Movie> = mutableListOf()
        results.forEach {
            movies.add(
                Movie(
                    page = page,
                    id = it.id,
                    title = it.title,
                    name = it.name,
                    overview = it.overview,
                    imageURi = it.imageURi,
                    backgroundURi = it.backgroundURi,
                    averageRating = it.averageRating,
                    popularity = it.popularity,
                    mediaType = it.mediaType,
                )
            )
        }
        return movies.toList()
    }
}

data class MoviesListItem(

    @SerializedName("id")
    @PrimaryKey val id: Int,

    @SerializedName("title")
    val title: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("poster_path")
    val imageURi:String?,

    @SerializedName("backdrop_path")
    val backgroundURi: String,

    @SerializedName("vote_average")
    val averageRating: Float,


    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("media_type")
    val mediaType: String,
)