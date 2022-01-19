package com.example.hw6architecture.data.local

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.hw6architecture.moviedetails.Actor
import com.example.hw6architecture.movielist.Movie


@Database(
    entities = [Movie::class, Actor::class, MoviesAndActorsJoin::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = Migrate_1_2::class)
    ]
)
abstract class MoviesRoom : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
    abstract fun actorsDao(): ActorsDao
    abstract fun moviesJoinActorsDao(): MoviesJoinActorsDao

    @VisibleForTesting
    internal val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE ${Movie.TABLE_NAME} ADD COLUMN favorite BOOLEAN")
        }
    }
}

