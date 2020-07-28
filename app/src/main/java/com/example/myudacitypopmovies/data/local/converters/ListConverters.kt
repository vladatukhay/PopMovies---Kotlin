package com.example.myudacitypopmovies.data.local.converters

import androidx.room.TypeConverter
import com.example.myudacitypopmovies.data.local.model.entities.Genre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ListConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromGenresList(genres: List<Genre?>?): String {
        return gson.toJson(genres)
    }

    @TypeConverter
    fun toGenresList(genres: String?): List<Genre> {
        if (genres == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<Genre?>?>() {}.type
        return gson.fromJson(genres, listType)
    }
}