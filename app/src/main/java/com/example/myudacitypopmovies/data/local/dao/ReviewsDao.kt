package com.example.myudacitypopmovies.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.myudacitypopmovies.data.local.model.entities.Review

@Dao
interface ReviewsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllReviews(reviews: List<Review?>?)
}