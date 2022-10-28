package com.erapps.moviesinfoapp.data.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.erapps.moviesinfoapp.data.room.entities.MovieListEntity

@Dao
interface MovieListDao {

    @Query("select * from cached_movie_list")
    suspend fun getCachedMovies(): MovieListEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movieListEntity: MovieListEntity)

    @Query("delete from cached_movie_list where id = 0")
    suspend fun clearMovies()
}