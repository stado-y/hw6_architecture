package com.example.hw6architecture.data.network

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hw6architecture.data.network.MovieCreditsResponse.Companion.TABLE_NAME
import com.google.gson.annotations.SerializedName



@Entity(tableName = TABLE_NAME)
data class MovieCreditsResponse(

    @SerializedName("id")
    @PrimaryKey
    val movieId: Int,

    @SerializedName("cast")
    val cast: List<MovieActor>,
) {

    companion object {

        const val TABLE_NAME = "credits_table"
    }
}


data class MovieActor(

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