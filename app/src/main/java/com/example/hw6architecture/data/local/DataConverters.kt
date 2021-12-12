package com.example.hw6architecture.data.local

import androidx.room.TypeConverter
import com.example.hw6architecture.data.network.MovieActor
import com.google.gson.Gson

public class DataConverters {

    @TypeConverter
    fun fromMovieCast(list: List<MovieActor>): String {

        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun toMovieCast(jsonString: String): List<MovieActor> {

        val gson = Gson()
        return gson.fromJson(jsonString, Array<MovieActor>::class.java).asList()
    }
}