package com.example.hw6architecture.movielist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hw6architecture.movielist.Movie.Companion.TABLE_NAME


@Entity(tableName = TABLE_NAME)
data class Movie(
    @PrimaryKey val id: Int,
    val title: String?,
    val name: String?,
    val overview: String,
    val imageURi:String?,
    val backgroundURi: String,
    val averageRating: Float,
    val popularity: Double,
    val mediaType: String,
    val page: Int,
    @ColumnInfo(defaultValue = "0") var favorite: Boolean,
) {
    companion object {
        const val TABLE_NAME = "movies_table"
    }
}