package com.example.myudacitypopmovies.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myudacitypopmovies.data.MoviesRepository
import com.example.myudacitypopmovies.ui.moviedetails.MovieDetailsViewModel
import com.example.myudacitypopmovies.ui.movielist.discover.DiscoverMoviesViewModel
import com.example.myudacitypopmovies.ui.movielist.favorites.FavoritesViewModel

class ViewModelFactory(private val repository: MoviesRepository?) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiscoverMoviesViewModel::class.java)) {
            return DiscoverMoviesViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            return FavoritesViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(repository!!) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        fun getInstance(repository: MoviesRepository?): ViewModelFactory {
            return ViewModelFactory(repository)
        }
    }

}