package com.example.myudacitypopmovies.data.local.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "cast", indices = Index("movie_id"), foreignKeys = ForeignKey(entity = Movie::class, parentColumns = "id", childColumns = "movie_id", onDelete = CASCADE, onUpdate = CASCADE))
class Cast(@field:SerializedName("credit_id") @field:PrimaryKey val id: String, @field:ColumnInfo(name = "movie_id") var movieId: Long, @field:SerializedName("name") val actorName: String, @field:SerializedName("character") val characterName: String, @field:SerializedName("order") val order: Int, @field:SerializedName("profile_path") @field:ColumnInfo(name = "profile_path") val profilePath: String) : Serializable