package com.example.myudacitypopmovies.ui.moviedetails.trailers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myudacitypopmovies.R
import com.example.myudacitypopmovies.data.local.model.entities.Trailer
import com.example.myudacitypopmovies.databinding.ItemTrailerBinding
import com.example.myudacitypopmovies.utils.GlideApp

class TrailerViewHolder(private val binding: ItemTrailerBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
    fun bindTo(trailer: Trailer) {
        val thumbnail = "https://img.youtube.com/vi/" + trailer.key + "/hqdefault.jpg"
        GlideApp.with(context)
                .load(thumbnail)
                .placeholder(R.color.md_grey_200)
                .into(binding.imageTrailer)
        binding.trailerName.setText(trailer.getName())
        //        binding.cardTrailer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.getKey()));
//                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.YOUTUBE_WEB_URL + trailer.getKey()));
//                if (appIntent.resolveActivity(context.getPackageManager()) != null) {
//                    context.startActivity(appIntent);
//                } else {
//                    context.startActivity(webIntent);
//                }
//            }
//        });
//        binding.executePendingBindings();
    }

    companion object {
        fun create(parent: ViewGroup): TrailerViewHolder {
            // Inflate
            val layoutInflater = LayoutInflater.from(parent.context)
            // Create the binding
            val binding = ItemTrailerBinding.inflate(layoutInflater, parent, false)
            return TrailerViewHolder(binding, parent.context)
        }
    }

}