package com.example.myudacitypopmovies.data.local.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.myudacitypopmovies.data.local.converters.ListConverters
import com.example.myudacitypopmovies.data.local.model.CastResponse
import com.example.myudacitypopmovies.data.local.model.ReviewsResponse
import com.example.myudacitypopmovies.data.local.model.TrailersResponse
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

@Entity(tableName = "movie")
class Movie(@field:SerializedName("id") @field:PrimaryKey(autoGenerate = true) var id: Long, @field:SerializedName("title") var title: String, @field:SerializedName("vote_count") @field:ColumnInfo(name = "vote_count") var voteCount: Int, @field:SerializedName("vote_average") @field:ColumnInfo(name = "vote_average") var voteAverage: Double, @field:SerializedName("poster_path") @field:ColumnInfo(name = "poster_path") var posterPath: String,
            @field:SerializedName("backdrop_path") @field:ColumnInfo(name = "backdrop_path") var backdropPath: String, @field:SerializedName("overview") var overview: String, @field:SerializedName("release_date") @field:ColumnInfo(name = "release_date") var releaseDate: String, @field:SerializedName("popularity") var popularity: Double,
            @field:TypeConverters(ListConverters::class) @field:SerializedName("genre") var genre: List<Genre>, @field:ColumnInfo(name = "is_favorite") var isFavorite: Boolean) : Serializable {

    @Ignore
    @SerializedName("videos")
    private val trailersResponse: TrailersResponse? = null

    @Ignore
    @SerializedName("credits")
    private val castResponse: CastResponse? = null

    @Ignore
    @SerializedName("reviews")
    private val reviewsResponse: ReviewsResponse? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val movie = o as Movie
        return id === movie.id && title === movie.title && posterPath === movie.posterPath && overview === movie.overview && movie.popularity.compareTo(popularity) == 0 && movie.voteAverage.compareTo(voteAverage) == 0 && releaseDate === movie.releaseDate
    }

    override fun hashCode(): Int {
        return Objects.hash(id, title, posterPath, overview, popularity, voteAverage, releaseDate)
    }

}