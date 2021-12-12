package com.example.hw6architecture.data.network

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hw6architecture.data.network.MoviesListItem.Companion.TABLE_NAME
import com.google.gson.annotations.SerializedName

data class MoviesListResponse(
    val page: Int,
    val results: List<MoviesListItem>,
    val total_pages: Int,
    val total_results: Int,
)

@Entity(tableName = TABLE_NAME)
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
) {

    companion object {

        const val TABLE_NAME = "movies_table"
    }
}