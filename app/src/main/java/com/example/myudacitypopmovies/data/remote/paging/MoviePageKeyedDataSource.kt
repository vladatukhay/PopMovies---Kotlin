package com.example.myudacitypopmovies.data.remote.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.myudacitypopmovies.data.local.model.MoviesResponse
import com.example.myudacitypopmovies.data.local.model.Resource
import com.example.myudacitypopmovies.data.local.model.entities.Movie
import com.example.myudacitypopmovies.data.remote.api.MovieService
import com.example.myudacitypopmovies.ui.movielist.MoviesFilterType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.Executor

class MoviePageKeyedDataSource(private val movieService: MovieService,
                               private val networkExecutor: Executor, private val sortBy: MoviesFilterType) : PageKeyedDataSource<Int, Movie?>() {
    var networkState = MutableLiveData<Resource<*>>()
    var retryCallback: RetryCallback? = null
    override fun loadInitial(params: LoadInitialParams<Int>,
                             callback: LoadInitialCallback<Int, Movie?>) {
        networkState.postValue(Resource.loading(null))

        // load data from API
        val request: Call<MoviesResponse>
        request = if (sortBy == MoviesFilterType.POPULAR) {
            movieService.getPopularMovies(FIRST_PAGE)
        } else {
            movieService.getTopRatedMovies(FIRST_PAGE)
        }

        // we execute sync since this is triggered by refresh
        try {
            val response = request.execute()
            val data = response.body()
            val movieList: List<Movie?>? = if (data != null) data.movies else emptyList<Movie>()
            retryCallback = null
            networkState.postValue(Resource.success(null))
            callback.onResult(movieList!!, null, FIRST_PAGE + 1)
        } catch (e: IOException) {
            // publish error
            retryCallback = object : RetryCallback {
                override operator fun invoke() {
                    networkExecutor.execute { loadInitial(params, callback) }
                }
            }
            networkState.postValue(Resource.error(e.message!!, null))
        }
    }

    override fun loadBefore(params: LoadParams<Int>,
                            callback: LoadCallback<Int, Movie?>) {
        // ignored, since we only ever append to our initial load
    }

    override fun loadAfter(params: LoadParams<Int>,
                           callback: LoadCallback<Int, Movie?>) {
        networkState.postValue(Resource.loading(null))

        // load data from API
        val request: Call<MoviesResponse>
        request = if (sortBy == MoviesFilterType.POPULAR) {
            movieService.getPopularMovies(params.key)
        } else {
            movieService.getTopRatedMovies(params.key)
        }
        request.enqueue(object : Callback<MoviesResponse?> {
            override fun onResponse(call: Call<MoviesResponse?>, response: Response<MoviesResponse?>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    val movieList: List<Movie?>? = if (data != null) data.movies else emptyList<Movie>()
                    retryCallback = null
                    callback.onResult(movieList!!, params.key + 1)
                    networkState.postValue(Resource.success(null))
                } else {
                    retryCallback = object : RetryCallback {
                        override operator fun invoke() {
                            loadAfter(params, callback)
                        }
                    }
                    networkState.postValue(Resource.error("error code: " + response.code(), null))
                }
            }

            override fun onFailure(call: Call<MoviesResponse?>, t: Throwable) {
                retryCallback = object : RetryCallback {
                    override operator fun invoke() {
                        networkExecutor.execute { loadAfter(params, callback) }
                    }
                }
                networkState.postValue(Resource.error(if (t != null) t.message!! else "unknown error", null))
            }
        })
    }

    interface RetryCallback {
        operator fun invoke()
    }

    companion object {
        private const val FIRST_PAGE = 1
    }

}