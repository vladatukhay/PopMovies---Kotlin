package com.example.myudacitypopmovies.data.local.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "genre")
class Genre(@field:SerializedName("id") @field:PrimaryKey val id: Int, @field:SerializedName("name") val name: String) : Serializable