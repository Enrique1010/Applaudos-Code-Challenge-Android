package com.erapps.moviesinfoapp.di

import com.erapps.moviesinfoapp.BuildConfig
import com.erapps.moviesinfoapp.data.api.NetworkResponseAdapterFactory
import com.erapps.moviesinfoapp.data.api.TheMovieDBApiService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideOkHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(okHttpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.apply {
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
        }
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(okHttpLoggingInterceptor)
        }
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        val json = Gson().newBuilder().setLenient().create()

        return GsonConverterFactory.create(json)
    }

    @Singleton
    @Provides
    fun provideRetrofitTheMovieDBApiInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.The_Movie_DB_Api_Base_URL)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideTheMovieDBApiService(retrofit: Retrofit): TheMovieDBApiService {
        return retrofit.create(TheMovieDBApiService::class.java)
    }
}