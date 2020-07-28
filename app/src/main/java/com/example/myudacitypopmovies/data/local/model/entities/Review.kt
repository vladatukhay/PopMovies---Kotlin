package com.example.myudacitypopmovies.data.local.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "review", indices = Index("movie_id"), foreignKeys = ForeignKey(entity = Movie::class, parentColumns = "id", childColumns = "movie_id", onDelete = CASCADE, onUpdate = CASCADE))
class Review(@field:SerializedName("author") val author: String, @field:SerializedName("content") val content: String, @field:SerializedName("id") @field:PrimaryKey val id: String, @field:SerializedName("url") val url: String, @field:ColumnInfo(name = "movie_id") var movieId: Long) : Serializable