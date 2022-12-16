package com.github.filipelipan.moviesapp.moviedetail.data.repository

import com.github.filipelipan.moviesapp.infraestructure.model.Result
import com.github.filipelipan.moviesapp.moviedetail.domain.error.LoadMovieDetailError
import com.github.filipelipan.moviesapp.moviedetail.domain.model.MovieDetail

interface MovieDetailRepository {
    suspend fun loadMovieDetail(
        movieId: String,
        language: String,
    ): Result<MovieDetail, LoadMovieDetailError>
}
