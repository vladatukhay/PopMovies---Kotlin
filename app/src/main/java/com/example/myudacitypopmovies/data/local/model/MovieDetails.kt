package com.example.myudacitypopmovies.data.local.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.myudacitypopmovies.data.local.model.entities.Cast
import com.example.myudacitypopmovies.data.local.model.entities.Movie
import com.example.myudacitypopmovies.data.local.model.entities.Review
import com.example.myudacitypopmovies.data.local.model.entities.Trailer

/**
 * This class is used to load full movie details including (Trailers, Cast, etc)
 */
class MovieDetails(@field:Embedded var movie: Movie, @field:Relation(parentColumn = "id", entityColumn = "movie_id") val trailers: List<Trailer>, @field:Relation(parentColumn = "id", entityColumn = "movie_id") val castList: List<Cast>, @field:Relation(parentColumn = "id", entityColumn = "movie_id") val reviews: List<Review>)