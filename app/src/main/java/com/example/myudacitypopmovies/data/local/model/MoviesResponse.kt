package com.example.myudacitypopmovies.data.local.model

import com.example.myudacitypopmovies.data.local.model.entities.Movie
import com.google.gson.annotations.SerializedName

class MoviesResponse(@field:SerializedName("page") val page: Int, @field:SerializedName("total_results") val totalResults: Int, @field:SerializedName("total_pages") val totalPages: Int, @field:SerializedName("results") val movies: List<Movie>)