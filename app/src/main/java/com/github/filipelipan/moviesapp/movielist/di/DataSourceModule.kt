package com.github.filipelipan.moviesapp.movielist.di

import com.github.filipelipan.moviesapp.infraestructure.coroutines.CoroutineDispatcherProvider
import com.github.filipelipan.moviesapp.movielist.data.api.MovieListApi
import com.github.filipelipan.moviesapp.movielist.data.datasource.LoadMoviesDataSource
import com.github.filipelipan.moviesapp.movielist.data.datasource.LoadMoviesDataSourceDefault
import com.github.filipelipan.moviesapp.movielist.data.mapper.MoviesResponseToMoviesModelMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideSearchMoviesDataSource(
        movieApi: MovieListApi,
        coroutineDispatcherProvider: CoroutineDispatcherProvider,
        moviesResponseToMoviesModelMapper: MoviesResponseToMoviesModelMapper
    ): LoadMoviesDataSource {
        return LoadMoviesDataSourceDefault(
            movieApi = movieApi,
            coroutineDispatcherProvider = coroutineDispatcherProvider,
            moviesResponseToMoviesModelMapper = moviesResponseToMoviesModelMapper
        )
    }
}
