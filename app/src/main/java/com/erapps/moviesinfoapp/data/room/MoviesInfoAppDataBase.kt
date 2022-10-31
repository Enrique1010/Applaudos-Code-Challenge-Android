package com.erapps.moviesinfoapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.erapps.moviesinfoapp.data.api.models.TvShow
import com.erapps.moviesinfoapp.data.room.daos.FavsTvShowsDao
import com.erapps.moviesinfoapp.data.room.daos.TvShowListDao
import com.erapps.moviesinfoapp.data.room.entities.FavoriteTvShow

@Database(
    entities = [TvShow::class, FavoriteTvShow::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MoviesInfoAppDataBase : RoomDatabase() {
    abstract fun favsTvShowsDao(): FavsTvShowsDao
    abstract fun tvShowListDao(): TvShowListDao
}