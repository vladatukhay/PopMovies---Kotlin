package com.example.myudacitypopmovies.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.myudacitypopmovies.data.local.model.RepoMoviesResult
import com.example.myudacitypopmovies.data.local.model.entities.Movie
import com.example.myudacitypopmovies.data.remote.api.ApiResponse
import com.example.myudacitypopmovies.data.remote.api.MovieService
import com.example.myudacitypopmovies.data.remote.paging.MovieDataSourceFactory
import com.example.myudacitypopmovies.ui.movielist.MoviesFilterType
import com.example.myudacitypopmovies.utils.AppExecutors

class MoviesRemoteDataSource private constructor(private val mMovieService: MovieService,
                                                 private val mExecutors: AppExecutors) {
    fun loadMovie(movieId: Long): LiveData<ApiResponse<Movie>> {
        return mMovieService.getMovieDetails(movieId)
    }

    /**
     * Load movies for certain filter.
     */
    fun loadMoviesFilteredBy(sortBy: MoviesFilterType?): RepoMoviesResult {
        val sourceFactory = MovieDataSourceFactory(mMovieService, mExecutors.networkIO(), sortBy)

        // paging configuration
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE)
                .build()

        // Get the paged list
        val moviesPagedList = LivePagedListBuilder(sourceFactory, config)
                .setFetchExecutor(mExecutors.networkIO())
                .build()
        val networkState = Transformations.switchMap(sourceFactory.sourceLiveData) { input -> input.networkState }

        // Get pagedList and network errors exposed to the viewmodel
        return RepoMoviesResult(moviesPagedList, networkState, sourceFactory.sourceLiveData)
    }

    companion object {
        private const val PAGE_SIZE = 20

        @Volatile
        private var sInstance: MoviesRemoteDataSource? = null
        fun getInstance(movieService: MovieService,
                        executors: AppExecutors): MoviesRemoteDataSource? {
            if (sInstance == null) {
                synchronized(AppExecutors::class.java) {
                    if (sInstance == null) {
                        sInstance = MoviesRemoteDataSource(movieService, executors)
                    }
                }
            }
            return sInstance
        }
    }

}