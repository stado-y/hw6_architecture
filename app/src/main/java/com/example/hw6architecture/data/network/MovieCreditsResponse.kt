package com.example.hw6architecture.data.network

import com.example.hw6architecture.data.local.MoviesAndActorsJoin
import com.example.hw6architecture.moviedetails.Actor
import com.example.hw6architecture.moviedetails.MovieActorDomain
import com.google.gson.annotations.SerializedName


data class MovieCreditsResponse(

    @SerializedName("id")
    val movieId: Int,

    @SerializedName("cast")
    val cast: List<MovieCreditsActor>,
) {
    fun transformToListOfActors(): List<Actor> {
        val actors: ArrayList<Actor> = arrayListOf()
        cast.forEach {
            actors.add(
                Actor(
//                    movieId = movieId,
                    name = it.name,
                    photoURI = it.photoURI,
//                    character = it.character,
                    popularity = it.popularity,
//                    order = it.order,
                )
            )
        }
        actors.sortByDescending { it.popularity }
        return actors.toList()
    }

    fun transformToListOfMoviesAndActorsJoin(): ArrayList<MoviesAndActorsJoin> {
        val listOfJoins = arrayListOf<MoviesAndActorsJoin>()
        cast.forEach {
            listOfJoins.add(
                MoviesAndActorsJoin(
                    movieId = movieId,
                    actorName = it.name,
                    role = it.character,
                )
            )
        }
        return listOfJoins
    }

    fun transformToMovieActorDomain(): List<MovieActorDomain> {
        val listOfMovieActors = ArrayList<MovieActorDomain>()
        cast.forEach {
            listOfMovieActors.add(
                MovieActorDomain(
                    movieId = movieId,
                    name = it.name,
                    photoURI = it.photoURI,
                    popularity = it.popularity,
                    role = it.character,
                )
            )
        }
        return listOfMovieActors.toList()
    }
}

data class MovieCreditsActor(

    @SerializedName("name")
    val name: String,

    @SerializedName("profile_path")
    val photoURI: String?,

    @SerializedName("character")
    val character: String,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("order")
    val order: Int,
)