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

    companion object {

        const val DATABASE_NAME = "movies_database"

        @Volatile
        private var INSTANCE: MoviesRoom? = null

        fun getDatabase(
            context: Context,
        ): MoviesRoom {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    MoviesRoom::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}