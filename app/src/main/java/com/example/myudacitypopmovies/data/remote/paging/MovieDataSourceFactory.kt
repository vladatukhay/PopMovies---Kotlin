package com.example.myudacitypopmovies.data.remote.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.myudacitypopmovies.data.local.model.entities.Movie
import com.example.myudacitypopmovies.data.remote.api.MovieService
import com.example.myudacitypopmovies.ui.movielist.MoviesFilterType
import java.util.concurrent.Executor

class MovieDataSourceFactory(private val movieService: MovieService, private val networkExecutor: Executor, private val sortBy: MoviesFilterType) : DataSource.Factory<Int, Movie>() {
    var sourceLiveData = MutableLiveData<MoviePageKeyedDataSource>()
    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MoviePageKeyedDataSource(movieService, networkExecutor, sortBy)
        sourceLiveData.postValue(movieDataSource)
        return movieDataSource
    }

}