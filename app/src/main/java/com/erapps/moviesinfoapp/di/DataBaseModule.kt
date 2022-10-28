package com.erapps.moviesinfoapp.di

import android.content.Context
import androidx.room.Room
import com.erapps.moviesinfoapp.data.room.MoviesInfoAppDataBase
import com.erapps.moviesinfoapp.data.room.daos.FavsTvShowsDao
import com.erapps.moviesinfoapp.data.room.daos.MovieListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideMovieListDao(moviesInfoAppDataBase: MoviesInfoAppDataBase): MovieListDao {
        return moviesInfoAppDataBase.movieListDao()
    }

    @Singleton
    @Provides
    fun provideFavsTvShowsDaoDao(moviesInfoAppDataBase: MoviesInfoAppDataBase): FavsTvShowsDao {
        return moviesInfoAppDataBase.favsTvShowsDao()
    }

    @Singleton
    @Provides
    fun provideMoviesInfoAppDataBase(@ApplicationContext appContext: Context): MoviesInfoAppDataBase {
        return Room.databaseBuilder(
            appContext,
            MoviesInfoAppDataBase::class.java,
            "MoviesInfoApp_DB"
        ).fallbackToDestructiveMigration().build()
    }
}