package com.example.myudacitypopmovies.ui.movielist.favorites

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myudacitypopmovies.data.local.model.entities.Movie
import com.example.myudacitypopmovies.ui.movielist.MovieViewHolder

class FavoritesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mMoviesList: List<Movie>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = mMoviesList!![position]
        (holder as MovieViewHolder).bindTo(movie)
    }

    override fun getItemCount(): Int {
        return if (mMoviesList != null) mMoviesList!!.size else 0
    }

    fun submitList(movies: List<Movie>?) {
        mMoviesList = movies
        notifyDataSetChanged()
    }
}