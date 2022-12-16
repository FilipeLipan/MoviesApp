package com.github.filipelipan.moviesapp.movielist.di

import com.github.filipelipan.moviesapp.moviedetail.data.datasource.LoadMovieDetailDataSource
import com.github.filipelipan.moviesapp.moviedetail.domain.usecase.LoadMovieDetailCaseDefault
import com.github.filipelipan.moviesapp.moviedetail.domain.usecase.LoadMovieDetailUseCase
import com.github.filipelipan.moviesapp.movielist.domain.usecase.LoadMoviesUseCase
import com.github.filipelipan.moviesapp.movielist.domain.usecase.LoadMoviesUseCaseDefault
import com.github.filipelipan.moviesapp.movielist.data.datasource.LoadMoviesDataSource
import com.github.filipelipan.moviesapp.movielist.data.repository.LoadMoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideLoadMovieUseCase(
        loadMoviesRepository: LoadMoviesRepository
    ): LoadMoviesUseCase {
        return LoadMoviesUseCaseDefault(
            loadMoviesRepository = loadMoviesRepository
        )
    }
}
