package com.erapps.moviesinfoapp.di

import com.erapps.moviesinfoapp.data.source.TvShowsRepository
import com.erapps.moviesinfoapp.data.source.TvShowsRepositoryImp
import com.erapps.moviesinfoapp.data.source.local.TvShowsLocalDataSource
import com.erapps.moviesinfoapp.data.source.local.TvShowsLocalDataSourceImp
import com.erapps.moviesinfoapp.data.source.remote.TvShowsRemoteDataSource
import com.erapps.moviesinfoapp.data.source.remote.TvShowsRemoteDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TvShowsModule {

    @Binds
    @Singleton
    abstract fun provideTvShowsRemoteDataSource(
        tvShowsRemoteDataSourceImp: TvShowsRemoteDataSourceImp
    ): TvShowsRemoteDataSource

    @Binds
    @Singleton
    abstract fun provideTvShowsLocalDataSource(
        tvShowsLocalDataSourceImp: TvShowsLocalDataSourceImp
    ): TvShowsLocalDataSource

    @Binds
    @Singleton
    abstract fun provideTvShowRepository(
        tvShowsRepositoryImp: TvShowsRepositoryImp
    ): TvShowsRepository
}