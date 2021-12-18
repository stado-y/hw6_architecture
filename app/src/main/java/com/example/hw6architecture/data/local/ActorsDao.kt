package com.example.hw6architecture.data.local

import android.content.ContentValues.TAG
import android.util.Log
import androidx.room.*
import com.example.hw6architecture.moviedetails.Actor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Dao
interface ActorsDao {

    @Query("SELECT * FROM ${ Actor.TABLE_NAME } where movieId = :movieId")
    fun getActorsList(movieId: Int): List<Actor>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertActor(actor: Actor)

    suspend fun insertActorsList(actorsList: List<Actor>) {
        withContext(Dispatchers.IO) {
            for (actor in actorsList) {
                insertActor(actor)
            }
        }
    }
}