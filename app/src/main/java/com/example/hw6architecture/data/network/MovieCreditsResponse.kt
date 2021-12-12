package com.example.hw6architecture.data.network

import com.google.gson.annotations.SerializedName

data class MovieCreditsResponse(

    @SerializedName("id")
    val movieId: Int,

    @SerializedName("cast")
    val cast: List<MovieActor>,
)

data class MovieActor(

    @SerializedName("name")
    val name: String,

    @SerializedName("profile_path")
    val photoURI: String?,

    @SerializedName("character")
    val character: String,

    @SerializedName("popularity")
    val popularity: Double,
)