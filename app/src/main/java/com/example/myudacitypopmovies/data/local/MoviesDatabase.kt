package com.example.myudacitypopmovies.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myudacitypopmovies.data.local.converters.ListConverters
import com.example.myudacitypopmovies.data.local.dao.CastsDao
import com.example.myudacitypopmovies.data.local.dao.MoviesDao
import com.example.myudacitypopmovies.data.local.dao.ReviewsDao
import com.example.myudacitypopmovies.data.local.dao.TrailersDao
import com.example.myudacitypopmovies.data.local.model.entities.*

@Database(entities = [Cast::class, Genre::class, Movie::class, Review::class, Trailer::class], version = 1, exportSchema = false)
@TypeConverters(ListConverters::class)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun castsDao(): CastsDao?
    abstract fun moviesDao(): MoviesDao?
    abstract fun reviewsDao(): ReviewsDao?
    abstract fun trailersDao(): TrailersDao?

    companion object {
        const val DATABASE_NAME = "Movies.db"
        private var INSTANCE: MoviesDatabase? = null
        private val sLock = Any()
        fun getInstance(context: Context): MoviesDatabase? {
            synchronized(sLock) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context)
                }
                return INSTANCE
            }
        }

        private fun buildDatabase(context: Context): MoviesDatabase {
            return Room.databaseBuilder(
                    context.applicationContext,
                    MoviesDatabase::class.java,
                    DATABASE_NAME).build()
        }
    }
}