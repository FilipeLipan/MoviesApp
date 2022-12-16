package com.github.filipelipan.moviesapp.moviedetail.di

import com.github.filipelipan.moviesapp.moviedetail.data.repository.MovieDetailRepository
import com.github.filipelipan.moviesapp.moviedetail.domain.usecase.LoadMovieDetailCaseDefault
import com.github.filipelipan.moviesapp.moviedetail.domain.usecase.LoadMovieDetailUseCase
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
    fun provideLoadMovieDetailUseCase(
        movieDetailRepository: MovieDetailRepository
    ): LoadMovieDetailUseCase {
        return LoadMovieDetailCaseDefault(
            movieDetailRepository = movieDetailRepository,
        )
    }
}
