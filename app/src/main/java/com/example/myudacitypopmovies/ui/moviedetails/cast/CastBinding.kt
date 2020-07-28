package com.example.myudacitypopmovies.ui.moviedetails.cast

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myudacitypopmovies.data.local.model.entities.Cast

object CastBinding {
    @BindingAdapter("items")
    fun setItems(recyclerView: RecyclerView, items: List<Cast?>?) {
        val adapter = recyclerView.adapter as CastAdapter?
        adapter?.submitList(items)
    }
}