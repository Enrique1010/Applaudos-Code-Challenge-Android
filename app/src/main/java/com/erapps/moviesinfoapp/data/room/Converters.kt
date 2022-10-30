package com.erapps.moviesinfoapp.data.room

import androidx.room.TypeConverter
import com.erapps.moviesinfoapp.data.api.models.TvShow
import com.erapps.moviesinfoapp.data.room.entities.FavoriteTvShow
import com.erapps.moviesinfoapp.data.room.entities.MovieListEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converters {
    private val gson = Gson()

    //lists and entities
    @TypeConverter
    fun movieListEntityToString(movieListEntity: MovieListEntity): String? {
        return gson.toJson(movieListEntity)
    }

    @TypeConverter
    fun stringToMovieListEntity(json: String): MovieListEntity? {
        return gson.fromJson(json, MovieListEntity::class.java)
    }

    @TypeConverter
    fun tvShowListToString(tvShows: List<TvShow>): String? {
        return gson.toJson(tvShows)
    }

    @TypeConverter
    fun stringToTvShowList(json: String): List<TvShow>? {
        if (json.isEmpty()) return emptyList()

        val listType = object : TypeToken<List<TvShow>>() {}.type
        return gson.fromJson(json, listType)
    }

    @TypeConverter
    fun favsTvShowListToString(tvShows: List<FavoriteTvShow>): String? {
        return gson.toJson(tvShows)
    }

    @TypeConverter
    fun stringToFavsTvShowList(json: String): List<FavoriteTvShow>? {
        if (json.isEmpty()) return emptyList()

        val listType = object : TypeToken<List<FavoriteTvShow>>() {}.type
        return gson.fromJson(json, listType)
    }

    @TypeConverter
    fun stringListToString(tvShows: List<String>): String? {
        return gson.toJson(tvShows)
    }

    @TypeConverter
    fun stringToStringList(json: String): List<String>? {
        if (json.isEmpty()) return emptyList()

        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, listType)
    }

    @TypeConverter
    fun intListToString(tvShows: List<Int>): String? {
        return gson.toJson(tvShows)
    }

    @TypeConverter
    fun stringToIntList(json: String): List<Int>? {
        if (json.isEmpty()) return emptyList()

        val listType = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(json, listType)
    }

    //objects
    @TypeConverter
    fun tvShowToString(tvShow: TvShow): String? {
        return gson.toJson(tvShow)
    }

    @TypeConverter
    fun stringToTvShow(json: String): TvShow? {

        return gson.fromJson(json, TvShow::class.java)
    }

    @TypeConverter
    fun favTvShowToString(tvShow: FavoriteTvShow): String? {
        return gson.toJson(tvShow)
    }

    @TypeConverter
    fun stringToFavTvShow(json: String): FavoriteTvShow? {

        return gson.fromJson(json, FavoriteTvShow::class.java)
    }
}