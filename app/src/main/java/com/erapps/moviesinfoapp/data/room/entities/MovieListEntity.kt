package com.erapps.moviesinfoapp.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.erapps.moviesinfoapp.data.api.models.TvShow

@Entity(tableName = "cached_movie_list")
data class MovieListEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "tvShows")
    val tvShows: List<TvShow>,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long = System.currentTimeMillis()
)
