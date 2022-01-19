package com.example.hw6architecture.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hw6architecture.data.local.Actor.Companion.TABLE_NAME


@Entity(tableName = TABLE_NAME)
data class Actor(
    @PrimaryKey
    val name: String,
    val photoURI: String?,
    val popularity: Double,
) {
    companion object {
        const val TABLE_NAME = "actors_table"
    }
}