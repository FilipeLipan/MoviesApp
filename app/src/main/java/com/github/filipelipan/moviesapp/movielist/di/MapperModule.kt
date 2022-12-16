package com.github.filipelipan.moviesapp.movielist.di

import com.github.filipelipan.moviesapp.movielist.data.mapper.MoviesResponseToMoviesModelMapper
import com.github.filipelipan.moviesapp.movielist.data.mapper.MoviesResponseToMoviesModelMapperDefault
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
    fun provideMoviesResponseToMoviesModelMapper(): MoviesResponseToMoviesModelMapper {
        return MoviesResponseToMoviesModelMapperDefault()
    }
}