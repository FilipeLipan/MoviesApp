package com.github.filipelipan.moviesapp.movielist.di

import com.github.filipelipan.moviesapp.movielist.data.datasource.LoadMoviesDataSource
import com.github.filipelipan.moviesapp.movielist.data.repository.LoadMoviesRepository
import com.github.filipelipan.moviesapp.movielist.data.repository.LoadMoviesRepositoryDefault
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
    fun provideLoadMoviesRepository(
        loadMoviesDataSource: LoadMoviesDataSource
    ): LoadMoviesRepository {
        return LoadMoviesRepositoryDefault(
            loadMoviesDataSource = loadMoviesDataSource
        )
    }
}
