package com.example.myudacitypopmovies.ui.movielist.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myudacitypopmovies.data.local.model.Resource
import com.example.myudacitypopmovies.databinding.ItemNetworkStateBinding

class NetworkStateViewHolder(private val binding: ItemNetworkStateBinding,
                             viewModel: DiscoverMoviesViewModel) : RecyclerView.ViewHolder(binding.root) {
    fun bindTo(resource: Resource<*>?) {
        binding.resource = resource
        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup, viewModel: DiscoverMoviesViewModel): NetworkStateViewHolder {
            // Inflate
            val layoutInflater = LayoutInflater.from(parent.context)
            // Create the binding
            val binding = ItemNetworkStateBinding.inflate(layoutInflater, parent, false)
            return NetworkStateViewHolder(binding, viewModel)
        }
    }

    init {

        // Trigger retry event on click
        binding.retryButton.setOnClickListener { viewModel.retry() }
    }
}