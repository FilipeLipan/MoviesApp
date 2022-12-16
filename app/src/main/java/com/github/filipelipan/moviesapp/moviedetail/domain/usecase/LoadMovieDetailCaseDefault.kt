package com.github.filipelipan.moviesapp.moviedetail.domain.usecase

import com.github.filipelipan.moviesapp.infraestructure.model.Result
import com.github.filipelipan.moviesapp.moviedetail.data.repository.MovieDetailRepository
import com.github.filipelipan.moviesapp.moviedetail.domain.model.MovieDetail
import com.github.filipelipan.moviesapp.moviedetail.domain.error.LoadMovieDetailError

class LoadMovieDetailCaseDefault(
    private val movieDetailRepository: MovieDetailRepository
) : LoadMovieDetailUseCase {
    override suspend fun invoke(
        movieId: String,
    ): Result<MovieDetail, LoadMovieDetailError> {
        return movieDetailRepository.loadMovieDetail(
            movieId = movieId,
            language = "en-US",
        )
    }
}
