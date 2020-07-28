package com.example.myudacitypopmovies.ui.movielist.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.myudacitypopmovies.R
import com.example.myudacitypopmovies.data.MoviesRepository
import com.example.myudacitypopmovies.data.local.model.RepoMoviesResult
import com.example.myudacitypopmovies.data.local.model.Resource
import com.example.myudacitypopmovies.data.local.model.entities.Movie
import com.example.myudacitypopmovies.ui.movielist.MoviesFilterType

class DiscoverMoviesViewModel(moviesRepository: MoviesRepository) : ViewModel() {
    private val repoMoviesResult: LiveData<RepoMoviesResult>
    val pagedList: LiveData<PagedList<Movie>>
    val networkState: LiveData<Resource<*>>
    val currentTitle = MutableLiveData<Int>()
    private val sortBy = MutableLiveData<MoviesFilterType>()

    val currentSorting: MoviesFilterType?
        get() = sortBy.value

    fun setSortMoviesBy(id: Int) {
        var filterType: MoviesFilterType? = null
        var title: Int? = null
        when (id) {
            R.id.action_popular_movies -> {

                // check if already selected. no need to request API
                if (sortBy.value == MoviesFilterType.POPULAR) return
                filterType = MoviesFilterType.POPULAR
                title = R.string.action_popular
            }
            R.id.action_top_rated -> {
                if (sortBy.value == MoviesFilterType.TOP_RATED) return
                filterType = MoviesFilterType.TOP_RATED
                title = R.string.action_top_rated
            }
            else -> throw IllegalArgumentException("unknown sorting id")
        }
        sortBy.value = filterType
        currentTitle.value = title
    }

    // retry any failed requests.
    fun retry() {
        repoMoviesResult.value!!.sourceLiveData.value!!.retryCallback!!.invoke()
    }

    init {
        sortBy.value = MoviesFilterType.POPULAR
        currentTitle.value = R.string.action_popular
        repoMoviesResult = Transformations.map(sortBy) { sort -> moviesRepository.loadMoviesFilteredBy(sort!!) }
        pagedList = Transformations.switchMap(repoMoviesResult) { input -> input.data }
        networkState = Transformations.switchMap(repoMoviesResult) { input -> input.resource }
    }
}