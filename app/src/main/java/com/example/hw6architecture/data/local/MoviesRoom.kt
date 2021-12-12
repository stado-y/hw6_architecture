package com.example.hw6architecture.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.hw6architecture.data.network.MovieCreditsResponse
import com.example.hw6architecture.data.network.MoviesListItem


@Database(
    entities = [MoviesListItem::class, MovieCreditsResponse::class],
    version = 1
)
@TypeConverters(DataConverters::class)
abstract class MoviesRoom : RoomDatabase() {

    abstract fun moviesListDao(): MoviesListDao
    abstract fun MovieCreditsDao(): MovieCreditsDao



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