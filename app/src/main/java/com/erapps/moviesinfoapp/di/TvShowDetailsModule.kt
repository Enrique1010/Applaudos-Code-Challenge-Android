package com.erapps.moviesinfoapp.di

import com.erapps.moviesinfoapp.data.source.TvShowDetailsRepository
import com.erapps.moviesinfoapp.data.source.TvShowDetailsRepositoryImp
import com.erapps.moviesinfoapp.data.source.local.TvShowDetailsLocalDataSource
import com.erapps.moviesinfoapp.data.source.local.TvShowDetailsLocalDataSourceImp
import com.erapps.moviesinfoapp.data.source.remote.TvShowDetailsRemoteDataSource
import com.erapps.moviesinfoapp.data.source.remote.TvShowDetailsRemoteDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TvShowDetailsModule {

    @Binds
    @Singleton
    abstract fun provideTvShowDetailsRemoteDataSource(
        tvShowDetailsRemoteDataSourceImp: TvShowDetailsRemoteDataSourceImp
    ): TvShowDetailsRemoteDataSource

    @Binds
    @Singleton
    abstract fun provideTvShowDetailsLocalDataSource(
        tvShowDetailsLocalDataSourceImp: TvShowDetailsLocalDataSourceImp
    ): TvShowDetailsLocalDataSource

    @Binds
    @Singleton
    abstract fun provideTvShowDetailsRepository(
        tvShowDetailsRepositoryImp: TvShowDetailsRepositoryImp
    ): TvShowDetailsRepository
}