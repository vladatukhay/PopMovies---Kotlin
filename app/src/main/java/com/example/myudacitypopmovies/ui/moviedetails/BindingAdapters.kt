package com.example.myudacitypopmovies.ui.moviedetails

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.myudacitypopmovies.R
import com.example.myudacitypopmovies.data.local.model.entities.Genre
import com.example.myudacitypopmovies.utils.Constants
import com.example.myudacitypopmovies.utils.GlideApp
import com.example.myudacitypopmovies.utils.UiUtils
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

object BindingAdapters {
    @BindingAdapter("imageUrl", "isBackdrop")
    fun bindImage(imageView: ImageView, imagePath: String, isBackdrop: Boolean) {
        val baseUrl: String
        baseUrl = if (isBackdrop) {
            Constants.BACKDROP_URL
        } else {
            Constants.IMAGE_URL
        }
        GlideApp.with(imageView.context)
                .load(baseUrl + imagePath)
                .placeholder(R.color.md_grey_200)
                .into(imageView)
    }

    /**
     * Movie details poster image
     */
    @BindingAdapter("imageUrl")
    fun bindImage(imageView: ImageView, imagePath: String) {
        GlideApp.with(imageView.context)
                .load(Constants.IMAGE_URL + imagePath)
                .placeholder(R.color.md_grey_200)
                .apply(RequestOptions().transform(CenterCrop(),
                        RoundedCorners(UiUtils.dipToPixels(imageView.context, 8f).toInt())))
                .into(imageView)
    }

    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        if (show) view.visibility = View.VISIBLE else view.visibility = View.GONE
    }

    @BindingAdapter("items")
    fun setItems(view: ChipGroup, genres: List<Genre>?) {
        if (genres == null // Since we are using liveData to observe data, any changes in that table(favorites)
                // will trigger the observer and hence rebinding data, which can lead to duplicates.
                || view.childCount > 0) return

        // dynamically create & add genre chips
        val context = view.context
        for ((_, name) in genres) {
            val chip = Chip(context)
            chip.text = name
            chip.chipStrokeWidth = UiUtils.dipToPixels(context, 1f)
            chip.chipStrokeColor = ColorStateList.valueOf(
                    context.resources.getColor(R.color.md_blue_grey_200))
            chip.setChipBackgroundColorResource(android.R.color.transparent)
            view.addView(chip)
        }
    }
}