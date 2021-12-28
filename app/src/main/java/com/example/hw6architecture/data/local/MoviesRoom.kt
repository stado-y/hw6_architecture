package com.example.hw6architecture.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hw6architecture.moviedetails.Actor
import com.example.hw6architecture.movielist.Movie


@Database(
    entities = [Movie::class, Actor::class],
    version = 1
)
abstract class MoviesRoom : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
    abstract fun actorsDao(): ActorsDao
}