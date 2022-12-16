package com.github.filipelipan.moviesapp.movielist.di

import com.github.filipelipan.moviesapp.movielist.data.api.MovieListApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieListApiModule {

    @Singleton
    @Provides
    fun provideMovieListApi(
        retrofit: Retrofit
    ): MovieListApi {
        return retrofit.create(MovieListApi::class.java)
    }

}