package com.example.myudacitypopmovies.ui.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.myudacitypopmovies.R
import com.example.myudacitypopmovies.data.MoviesRepository
import com.example.myudacitypopmovies.data.local.model.MovieDetails
import com.example.myudacitypopmovies.data.local.model.Resource
import com.example.myudacitypopmovies.utils.SnackbarMessage
import timber.log.Timber

class MovieDetailsViewModel(private val repository: MoviesRepository) : ViewModel() {
    var result: LiveData<Resource<MovieDetails>>? = null
        private set
    private val movieIdLiveData = MutableLiveData<Long>()
    val snackbarMessage = SnackbarMessage()
    var isFavorite = false
    fun init(movieId: Long) {
        if (result != null) {
            return  // load movie details only once when the activity created first time
        }
        Timber.d("Initializing viewModel")
        result = Transformations.switchMap(movieIdLiveData) { movieId -> repository.loadMovie(movieId!!) }
        setMovieIdLiveData(movieId) // trigger loading movie
    }

    private fun setMovieIdLiveData(movieId: Long) {
        movieIdLiveData.value = movieId
    }

    fun retry(movieId: Long) {
        setMovieIdLiveData(movieId)
    }

    fun onFavoriteClicked() {
        val movieDetails = result!!.value!!.data
        if (!isFavorite) {
            repository.favoriteMovie(movieDetails!!.movie!!)
            isFavorite = true
            showSnackbarMessage(R.string.movie_added_successfully)
        } else {
            repository.unfavoriteMovie(movieDetails!!.movie!!)
            isFavorite = false
            showSnackbarMessage(R.string.movie_removed_successfully)
        }
    }

    private fun showSnackbarMessage(message: Int) {
        snackbarMessage.value = message
    }

}