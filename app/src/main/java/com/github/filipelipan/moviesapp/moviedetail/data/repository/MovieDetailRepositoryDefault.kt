package com.github.filipelipan.moviesapp.moviedetail.data.repository

import com.github.filipelipan.moviesapp.infraestructure.model.Result
import com.github.filipelipan.moviesapp.moviedetail.data.datasource.LoadMovieDetailDataSource
import com.github.filipelipan.moviesapp.moviedetail.domain.error.LoadMovieDetailError
import com.github.filipelipan.moviesapp.moviedetail.domain.model.MovieDetail

class MovieDetailRepositoryDefault(
    private val loadMovieDetailDataSource: LoadMovieDetailDataSource
) : MovieDetailRepository {
    override suspend fun loadMovieDetail(
        movieId: String,
        language: String,
    ): Result<MovieDetail, LoadMovieDetailError> {
        return loadMovieDetailDataSource.loadMovieDetail(
            movieId = movieId,
            language = language,
        )
    }
}
