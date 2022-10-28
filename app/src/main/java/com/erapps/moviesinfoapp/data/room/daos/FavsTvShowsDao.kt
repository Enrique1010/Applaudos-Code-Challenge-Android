package com.erapps.moviesinfoapp.data.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.erapps.moviesinfoapp.data.api.models.TvShow

@Dao
interface FavsTvShowsDao {

    @Query("select * from fav_tvShow")
    suspend fun getFavsTvShows(): List<TvShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavTvShow(tvShow: TvShow)

    @Query("delete from fav_tvShow where id = :id")
    suspend fun deleteFavTvShow(id: Int)
}