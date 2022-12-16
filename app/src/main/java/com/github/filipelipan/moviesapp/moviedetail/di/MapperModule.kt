package com.github.filipelipan.moviesapp.moviedetail.di

import com.github.filipelipan.moviesapp.moviedetail.data.mapper.MovieDetailResponseToMovieDetailMapper
import com.github.filipelipan.moviesapp.moviedetail.data.mapper.MovieDetailResponseToMovieDetailMapperDefault
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {
    @Singleton
    @Provides
    fun provideMovieDetailResponseToMovieDetailMapperDefault(): MovieDetailResponseToMovieDetailMapper {
        return MovieDetailResponseToMovieDetailMapperDefault()
    }
}