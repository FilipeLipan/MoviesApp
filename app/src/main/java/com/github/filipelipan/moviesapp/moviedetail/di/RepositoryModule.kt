package com.github.filipelipan.moviesapp.moviedetail.di

import com.github.filipelipan.moviesapp.moviedetail.data.datasource.LoadMovieDetailDataSource
import com.github.filipelipan.moviesapp.moviedetail.data.repository.MovieDetailRepository
import com.github.filipelipan.moviesapp.moviedetail.data.repository.MovieDetailRepositoryDefault
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideMovieDetailRepository(
        loadMovieDetailDataSource: LoadMovieDetailDataSource
    ): MovieDetailRepository {
        return MovieDetailRepositoryDefault(
            loadMovieDetailDataSource = loadMovieDetailDataSource
        )
    }
}
