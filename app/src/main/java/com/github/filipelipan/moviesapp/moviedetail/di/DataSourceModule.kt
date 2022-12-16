package com.github.filipelipan.moviesapp.moviedetail.di

import com.github.filipelipan.moviesapp.infraestructure.coroutines.CoroutineDispatcherProvider
import com.github.filipelipan.moviesapp.moviedetail.data.api.MovieDetailApi
import com.github.filipelipan.moviesapp.moviedetail.data.datasource.LoadMovieDetailDataSource
import com.github.filipelipan.moviesapp.moviedetail.data.datasource.LoadMovieDetailDataSourceDefault
import com.github.filipelipan.moviesapp.moviedetail.data.mapper.MovieDetailResponseToMovieDetailMapper
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
    fun provideLoadMovieDetailDataSource(
        movieDetailApi: MovieDetailApi,
        coroutineDispatcherProvider: CoroutineDispatcherProvider,
        movieDetailResponseToMovieDetailMapper: MovieDetailResponseToMovieDetailMapper,
    ): LoadMovieDetailDataSource {
        return LoadMovieDetailDataSourceDefault(
            movieDetailApi = movieDetailApi,
            coroutineDispatcherProvider = coroutineDispatcherProvider,
            movieDetailResponseToMovieDetailMapper = movieDetailResponseToMovieDetailMapper
        )
    }
}
