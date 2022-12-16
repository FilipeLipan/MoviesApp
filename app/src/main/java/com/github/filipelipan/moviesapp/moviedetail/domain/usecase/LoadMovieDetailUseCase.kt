package com.github.filipelipan.moviesapp.moviedetail.domain.usecase

import com.github.filipelipan.moviesapp.infraestructure.model.Result
import com.github.filipelipan.moviesapp.moviedetail.domain.model.MovieDetail
import com.github.filipelipan.moviesapp.moviedetail.domain.error.LoadMovieDetailError

interface LoadMovieDetailUseCase {
    suspend operator fun invoke(
        movieId: String,
    ): Result<MovieDetail, LoadMovieDetailError>
}
