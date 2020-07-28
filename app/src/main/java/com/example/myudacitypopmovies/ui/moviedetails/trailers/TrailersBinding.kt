package com.example.myudacitypopmovies.ui.moviedetails.trailers

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myudacitypopmovies.data.local.model.entities.Trailer

object TrailersBinding {
    @BindingAdapter("items")
    fun setItems(recyclerView: RecyclerView, items: List<Trailer?>?) {
        val adapter = recyclerView.adapter as TrailersAdapter?
        adapter?.submitList(items)
    }
}