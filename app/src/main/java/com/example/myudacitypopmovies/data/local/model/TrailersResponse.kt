package com.example.myudacitypopmovies.data.local.model

import com.example.myudacitypopmovies.data.local.model.entities.Trailer
import com.google.gson.annotations.SerializedName

class TrailersResponse(@field:SerializedName("results") val trailers: List<Trailer>)