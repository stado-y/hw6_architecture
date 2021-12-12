package com.example.hw6architecture.data.network

import com.google.gson.annotations.SerializedName

data class MoviesListResponse(
    val page: Int,
    val results: List<MoviesListItem>,
    val total_pages: Int,
    val total_results: Int,
)

data class MoviesListItem(

    @SerializedName("id")
    val id: Int,

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

    @SerializedName("genre_ids")
    val genres_list: List<Int>,

    @SerializedName("original_language")
    val originalLanguage: String,

    @SerializedName("original_title")
    val originalTitle: String,

    @SerializedName("video")
    val video: Boolean,

    @SerializedName("vote_average")
    val averageRating: Float,

    @SerializedName("vote_count")
    val timesVoted: Int,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("adult")
    val forAdults: Boolean,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("media_type")
    val mediaType: String,
)