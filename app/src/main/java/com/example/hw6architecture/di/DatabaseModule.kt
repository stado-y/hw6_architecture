package com.example.hw6architecture.di

import android.content.Context
import androidx.room.Room
import com.example.hw6architecture.data.local.ActorsDao
import com.example.hw6architecture.data.local.MoviesDao
import com.example.hw6architecture.data.local.MoviesRoom
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val DATABASE_NAME = "movies_database"

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideActorsDao(database: MoviesRoom): ActorsDao {
        return database.actorsDao()
    }

    @Provides
    @Singleton
    fun provideMoviesDao(database: MoviesRoom): MoviesDao {
        return database.moviesDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): MoviesRoom {
        return Room.databaseBuilder(
            appContext,
            MoviesRoom::class.java,
            DATABASE_NAME,
        ).build()
    }
}