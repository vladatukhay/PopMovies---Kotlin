package com.example.myudacitypopmovies.ui.moviedetails.reviews

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myudacitypopmovies.data.local.model.entities.Review

object ReviewsBinding {
    @BindingAdapter("items")
    fun setItems(recyclerView: RecyclerView, items: List<Review?>?) {
        val adapter = recyclerView.adapter as ReviewsAdapter?
        adapter?.submitList(items)
    }
}