package com.example.myudacitypopmovies.data

import androidx.lifecycle.LiveData
import com.example.myudacitypopmovies.data.local.model.MovieDetails
import com.example.myudacitypopmovies.data.local.model.RepoMoviesResult
import com.example.myudacitypopmovies.data.local.model.Resource
import com.example.myudacitypopmovies.data.local.model.entities.Movie
import com.example.myudacitypopmovies.ui.movielist.MoviesFilterType

interface DataSource {
    fun loadMovie(movieId: Long): LiveData<Resource<MovieDetails?>?>?
    fun loadMoviesFilteredBy(sortBy: MoviesFilterType?): RepoMoviesResult?
    val allFavoriteMovies: LiveData<List<Movie?>?>?
    fun favoriteMovie(movie: Movie?)
    fun unfavoriteMovie(movie: Movie?)
}