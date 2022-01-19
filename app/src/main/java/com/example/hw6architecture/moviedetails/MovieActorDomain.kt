package com.example.hw6architecture.moviedetails

data class MovieActorDomain(
    val movieId: Int,
    val name: String,
    val photoURI: String?,
    val popularity: Double,
    val role: String,
)