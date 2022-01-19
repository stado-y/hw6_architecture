package com.example.hw6architecture.data.local

import androidx.room.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Dao
interface ActorsDao {

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