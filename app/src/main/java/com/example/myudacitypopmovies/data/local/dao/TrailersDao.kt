package com.example.myudacitypopmovies.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.myudacitypopmovies.data.local.model.entities.Trailer

@Dao
interface TrailersDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllTrailers(trailers: List<Trailer?>?)
}