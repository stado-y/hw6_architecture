package com.example.hw6architecture.moviedetails

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hw6architecture.moviedetails.Actor.Companion.TABLE_NAME


@Entity(tableName = TABLE_NAME)
data class Actor(
    val movieId: Int,
    @PrimaryKey
    val name: String,
    val photoURI: String?,
    val character: String,
    val popularity: Double,
    val order: Int,
) {
    companion object {
        const val TABLE_NAME = "actors_table"
    }
}