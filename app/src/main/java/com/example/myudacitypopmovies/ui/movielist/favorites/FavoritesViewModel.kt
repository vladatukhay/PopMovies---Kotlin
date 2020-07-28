package com.example.myudacitypopmovies.ui.movielist.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myudacitypopmovies.data.MoviesRepository
import com.example.myudacitypopmovies.data.local.model.entities.Movie

class FavoritesViewModel(repository: MoviesRepository) : ViewModel() {
    //private final MoviesRepository movieRepository;
    val favoriteListLiveData: LiveData<List<Movie>>

    init {
        favoriteListLiveData = repository.getAllFavoriteMovies()
    }
}