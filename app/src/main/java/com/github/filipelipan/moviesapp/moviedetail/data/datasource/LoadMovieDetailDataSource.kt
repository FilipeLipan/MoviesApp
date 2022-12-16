package com.github.filipelipan.moviesapp.moviedetail.data.datasource

import com.github.filipelipan.moviesapp.infraestructure.model.Result
import com.github.filipelipan.moviesapp.moviedetail.domain.model.MovieDetail
import com.github.filipelipan.moviesapp.moviedetail.domain.error.LoadMovieDetailError

interface LoadMovieDetailDataSource {
    suspend fun loadMovieDetail(
        movieId: String,
        language: String,
    ): Result<MovieDetail, LoadMovieDetailError>
}
