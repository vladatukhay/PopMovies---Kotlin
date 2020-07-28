package com.example.myudacitypopmovies.data

import androidx.lifecycle.LiveData
import com.example.myudacitypopmovies.data.local.MoviesLocalDataSource
import com.example.myudacitypopmovies.data.local.model.MovieDetails
import com.example.myudacitypopmovies.data.local.model.RepoMoviesResult
import com.example.myudacitypopmovies.data.local.model.Resource
import com.example.myudacitypopmovies.data.local.model.entities.Movie
import com.example.myudacitypopmovies.data.remote.MoviesRemoteDataSource
import com.example.myudacitypopmovies.data.remote.api.ApiResponse
import com.example.myudacitypopmovies.ui.movielist.MoviesFilterType
import com.example.myudacitypopmovies.utils.AppExecutors
import timber.log.Timber

class MoviesRepository private constructor(private val mLocalDataSource: MoviesLocalDataSource,
                                           private val mRemoteDataSource: MoviesRemoteDataSource,
                                           private val mExecutors: AppExecutors) : DataSource {
    override fun loadMovie(movieId: Long): LiveData<Resource<MovieDetails>> {
        return object : NetworkBoundResource<MovieDetails?, Movie?>(mExecutors) {
            protected override fun saveCallResult(item: Movie) {
                mLocalDataSource.saveMovie(item)
                Timber.d("Movie added to database")
            }

            override fun shouldFetch(data: MovieDetails?): Boolean {
                // only fetch fresh data if it doesn't exist in database
                return data == null
            }

            override fun loadFromDb(): LiveData<MovieDetails> {
                Timber.d("Loading movie from database")
                return mLocalDataSource.getMovie(movieId)!!
            }

            override fun createCall(): LiveData<ApiResponse<Movie>> {
                Timber.d("Downloading movie from network")
                return mRemoteDataSource.loadMovie(movieId)
            }

            override fun onFetchFailed() {
                // ignored
                Timber.d("Fetch failed!!")
            }
        }.asLiveData
    }

    override fun loadMoviesFilteredBy(sortBy: MoviesFilterType): RepoMoviesResult {
        return mRemoteDataSource.loadMoviesFilteredBy(sortBy)
    }

    override fun getAllFavoriteMovies(): LiveData<List<Movie>> {
        return mLocalDataSource.getAllFavoriteMovies()
    }

    override fun favoriteMovie(movie: Movie) {
        mExecutors.diskIO().execute {
            Timber.d("Adding movie to favorites")
            mLocalDataSource.favoriteMovie(movie)
        }
    }

    override fun unfavoriteMovie(movie: Movie) {
        mExecutors.diskIO().execute {
            Timber.d("Removing movie from favorites")
            mLocalDataSource.unfavoriteMovie(movie)
        }
    }

    companion object {
        @Volatile
        private var sInstance: MoviesRepository? = null
        fun getInstance(localDataSource: MoviesLocalDataSource,
                        remoteDataSource: MoviesRemoteDataSource,
                        executors: AppExecutors): MoviesRepository? {
            if (sInstance == null) {
                synchronized(MoviesRepository::class.java) {
                    if (sInstance == null) {
                        sInstance = MoviesRepository(localDataSource, remoteDataSource, executors)
                    }
                }
            }
            return sInstance
        }
    }

}