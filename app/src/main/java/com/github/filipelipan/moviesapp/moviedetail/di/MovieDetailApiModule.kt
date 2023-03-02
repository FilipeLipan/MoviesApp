package com.github.filipelipan.moviesapp.moviedetail.di

import com.github.filipelipan.moviesapp.moviedetail.data.api.MovieDetailApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieDetailApiModule {

    @Singleton
    @Provides
    fun provideMovieDetailApi(
        retrofit: Retrofit
    ): MovieDetailApi {
        return retrofit.create(MovieDetailApi::class.java)
    }
}
