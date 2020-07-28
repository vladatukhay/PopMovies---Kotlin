package com.example.myudacitypopmovies.data.local

import androidx.lifecycle.LiveData
import com.example.myudacitypopmovies.data.local.model.MovieDetails
import com.example.myudacitypopmovies.data.local.model.entities.Cast
import com.example.myudacitypopmovies.data.local.model.entities.Movie
import com.example.myudacitypopmovies.data.local.model.entities.Review
import com.example.myudacitypopmovies.data.local.model.entities.Trailer
import com.example.myudacitypopmovies.utils.AppExecutors
import timber.log.Timber

class MoviesLocalDataSource private constructor(private val mDatabase: MoviesDatabase) {
    fun saveMovie(movie: Movie?) {
        mDatabase.moviesDao().insertMovie(movie)
    }

    private fun insertReviews(reviews: List<Review>, movieId: Long) {
        for (review in reviews) {
            review.movieId = movieId
        }
        mDatabase.reviewsDao().insertAllReviews(reviews)
        Timber.d("%s reviews inserted into database.", reviews.size)
    }

    private fun insertCastList(castList: List<Cast>, movieId: Long) {
        for (cast in castList) {
            cast.movieId = movieId
        }
        mDatabase.castsDao().insertAllCasts(castList)
        Timber.d("%s cast inserted into database.", castList.size)
    }

    private fun insertTrailers(trailers: List<Trailer>, movieId: Long) {
        for (trailer in trailers) {
            trailer.movieId = movieId
        }
        mDatabase.trailersDao().insertAllTrailers(trailers)
        Timber.d("%s trailers inserted into database.", trailers.size)
    }

    fun getMovie(movieId: Long): LiveData<MovieDetails>? {
        Timber.d("Loading movie details.")
        return mDatabase.moviesDao().getMovie(movieId)
    }

    val allFavoriteMovies: LiveData<List<Movie>?>
        get() = mDatabase.moviesDao().getAllFavoriteMovies()

    fun favoriteMovie(movie: Movie) {
        mDatabase.moviesDao().favoriteMovie(movie.id)
    }

    fun unfavoriteMovie(movie: Movie) {
        mDatabase.moviesDao().unFavoriteMovie(movie.id)
    }

    companion object {
        @Volatile
        private var sInstance: MoviesLocalDataSource? = null
        fun getInstance(database: MoviesDatabase): MoviesLocalDataSource? {
            if (sInstance == null) {
                synchronized(AppExecutors::class.java) {
                    if (sInstance == null) {
                        sInstance = MoviesLocalDataSource(database)
                    }
                }
            }
            return sInstance
        }
    }

}