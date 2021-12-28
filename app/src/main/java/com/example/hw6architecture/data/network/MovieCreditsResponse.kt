package com.example.hw6architecture.data.network

import com.example.hw6architecture.moviedetails.Actor
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
                    movieId = movieId,
                    name = it.name,
                    photoURI = it.photoURI,
                    character = it.character,
                    popularity = it.popularity,
                    order = it.order,
                )
            )
        }
        actors.sortBy { it.order }
        return actors.toList()
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