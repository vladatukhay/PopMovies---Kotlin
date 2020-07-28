package com.example.myudacitypopmovies.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.myudacitypopmovies.data.local.model.entities.Cast

@Dao
interface CastsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllCasts(castList: List<Cast?>?)
}