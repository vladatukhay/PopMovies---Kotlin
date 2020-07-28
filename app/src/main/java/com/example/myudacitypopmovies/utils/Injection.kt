package com.example.myudacitypopmovies.utils

import android.content.Context
import com.example.myudacitypopmovies.data.MoviesRepository
import com.example.myudacitypopmovies.data.local.MoviesDatabase
import com.example.myudacitypopmovies.data.local.MoviesLocalDataSource
import com.example.myudacitypopmovies.data.local.MoviesLocalDataSource.Companion.getInstance
import com.example.myudacitypopmovies.data.remote.MoviesRemoteDataSource
import com.example.myudacitypopmovies.data.remote.MoviesRemoteDataSource.Companion.getInstance
import com.example.myudacitypopmovies.data.remote.api.ApiClient
import com.example.myudacitypopmovies.data.remote.api.MovieService
import com.example.myudacitypopmovies.utils.AppExecutors.Companion.instance

/**
 * Class that handles object creation.
 */
object Injection {
    /**
     * Creates an instance of MoviesRemoteDataSource
     */
    fun provideMoviesRemoteDataSource(): MoviesRemoteDataSource? {
        val apiService: MovieService = ApiClient.getInstance()
        val executors = instance
        return getInstance(apiService, executors!!)
    }

    /**
     * Creates an instance of MoviesRemoteDataSource
     */
    fun provideMoviesLocalDataSource(context: Context): MoviesLocalDataSource? {
        val database = MoviesDatabase.getInstance(context.applicationContext)
        return getInstance(database)
    }

    /**
     * Creates an instance of MovieRepository
     */
    fun provideMovieRepository(context: Context): MoviesRepository {
        val remoteDataSource = provideMoviesRemoteDataSource()
        val localDataSource = provideMoviesLocalDataSource(context)
        val executors = instance
        return MoviesRepository.getInstance(
                localDataSource,
                remoteDataSource,
                executors)
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val repository = provideMovieRepository(context)
        return ViewModelFactory.getInstance(repository)
    }
}