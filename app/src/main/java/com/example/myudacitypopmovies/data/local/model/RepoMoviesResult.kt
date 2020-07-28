package com.example.myudacitypopmovies.data.local.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.myudacitypopmovies.data.local.model.entities.Movie
import com.example.myudacitypopmovies.data.remote.paging.MoviePageKeyedDataSource

class RepoMoviesResult(var data: LiveData<PagedList<Movie>>,
                       var resource: LiveData<Resource<*>>,
                       var sourceLiveData: MutableLiveData<MoviePageKeyedDataSource>) 