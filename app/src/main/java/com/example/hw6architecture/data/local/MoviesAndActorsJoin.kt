package com.example.hw6architecture.data.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import com.example.hw6architecture.data.local.MoviesAndActorsJoin.Companion.TABLE_NAME
import com.example.hw6architecture.moviedetails.Actor
import com.example.hw6architecture.movielist.Movie
import com.google.gson.annotations.SerializedName


@Entity(
    tableName = TABLE_NAME,
    primaryKeys = ["movieId", "actorName"],
    indices = [Index("movieId"), Index("actorName")],
    foreignKeys = [
        ForeignKey(
            entity = Movie::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = CASCADE,
        ),
        ForeignKey(
            entity = Actor::class,
            parentColumns = ["name"],
            childColumns = ["actorName"],
            onDelete = CASCADE,
        )
    ]
)
data class MoviesAndActorsJoin (
    @SerializedName("movieId")
    val movieId: Int,
    @SerializedName("actorName")
    val actorName: String,
    @SerializedName("role")
    val role: String,
) {
    companion object {
        const val TABLE_NAME = "movies_and_actors_join_table"
    }
}