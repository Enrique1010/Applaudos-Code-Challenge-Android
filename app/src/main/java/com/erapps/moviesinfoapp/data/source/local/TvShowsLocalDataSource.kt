package com.erapps.moviesinfoapp.data.source.local

import com.erapps.moviesinfoapp.data.room.daos.MovieListDao
import javax.inject.Inject

interface TvShowsLocalDataSource {
}

class TvShowsLocalDataSourceImp @Inject constructor(
    private val movieListDao: MovieListDao
): TvShowsLocalDataSource {

}