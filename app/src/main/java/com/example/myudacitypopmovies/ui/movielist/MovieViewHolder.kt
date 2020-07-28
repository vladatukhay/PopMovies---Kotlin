package com.example.myudacitypopmovies.ui.movielist

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myudacitypopmovies.data.local.model.entities.Movie
import com.example.myudacitypopmovies.databinding.ItemMovieBinding
import com.example.myudacitypopmovies.ui.moviedetails.DetailsActivity

class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindTo(movie: Movie) {
        binding.movie = movie
        // movie click event
        binding.root.setOnClickListener { view ->
            val intent = Intent(view.context, DetailsActivity::class.java)
            intent.putExtra(DetailsActivity.EXTRA_MOVIE_ID, movie.id)
            view.context.startActivity(intent)
        }
        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup): MovieViewHolder {
            // Inflate
            val layoutInflater = LayoutInflater.from(parent.context)
            // Create the binding
            val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
            return MovieViewHolder(binding)
        }
    }

}