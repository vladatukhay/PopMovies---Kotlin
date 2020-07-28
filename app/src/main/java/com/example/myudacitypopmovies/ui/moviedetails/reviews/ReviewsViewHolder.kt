package com.example.myudacitypopmovies.ui.moviedetails.reviews

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myudacitypopmovies.data.local.model.entities.Review
import com.example.myudacitypopmovies.databinding.ItemReviewBinding

class ReviewsViewHolder //binding.frame.setClipToOutline(false);
(private val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindTo(review: Review) {
        val userName = review.author

        //review user image
        binding.imageAuthor.setImageDrawable(Drawable.createFromPath(userName))
        // review's author
        binding.textAuthor.text = userName
        // review's content
        binding.textContent.text = review.content
        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup): ReviewsViewHolder {
            // Inflate
            val layoutInflater = LayoutInflater.from(parent.context)
            // Create the binding
            val binding = ItemReviewBinding.inflate(layoutInflater, parent, false)
            return ReviewsViewHolder(binding)
        }
    }

}