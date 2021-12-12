package com.example.hw6architecture.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.Serializable

interface ImdbApiClient: Serializable {

    @GET("trending/{media_type}/{time_window}")
    suspend fun getMoviesList(
        @Path("media_type") mediaType: String,
        @Path("time_window") timeWindow: String,
        @Query("page") page: Int = 1,
        //@Query("api_key") apiId: String = Constants.API_KEY,

    ): MoviesListResponse

    @GET("{media_type}/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("media_type") mediaType: String = "movie",
        @Path("movie_id") movieId: Int,
    ): MovieCreditsResponse

}