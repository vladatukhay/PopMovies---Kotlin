package com.example.myudacitypopmovies.data.local.model

import com.example.myudacitypopmovies.data.local.model.entities.Review
import com.google.gson.annotations.SerializedName

class ReviewsResponse(@field:SerializedName("results") val reviews: List<Review>)