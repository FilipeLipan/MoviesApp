package com.github.filipelipan.moviesapp.moviedetail.data.datasource

import com.github.filipelipan.moviesapp.infraestructure.coroutines.CoroutineDispatcherProvider
import com.github.filipelipan.moviesapp.infraestructure.coroutines.safeApiCall
import com.github.filipelipan.moviesapp.infraestructure.model.Result
import com.github.filipelipan.moviesapp.moviedetail.data.api.MovieDetailApi
import com.github.filipelipan.moviesapp.moviedetail.data.mapper.MovieDetailResponseToMovieDetailMapper
import com.github.filipelipan.moviesapp.moviedetail.domain.model.Genre
import com.github.filipelipan.moviesapp.moviedetail.domain.model.MovieDetail
import com.github.filipelipan.moviesapp.moviedetail.domain.error.LoadMovieDetailError
import org.intellij.lang.annotations.Language

class LoadMovieDetailDataSourceDefault(
    private val movieDetailApi: MovieDetailApi,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val movieDetailResponseToMovieDetailMapper: MovieDetailResponseToMovieDetailMapper,
) : LoadMovieDetailDataSource {

    override suspend fun loadMovieDetail(
        movieId: String,
        language: String,
    ): Result<MovieDetail, LoadMovieDetailError> {
        return safeApiCall(coroutineDispatcherProvider.io()) {
            movieDetailApi.loadMovieDetails(
                movieId = movieId,
                language = language
            )
        }
            .mapSuccess {
                return Result.Success(
                    movieDetailResponseToMovieDetailMapper.mapFrom(it)
                )
            }
            .mapError {
                return Result.Error(
                    LoadMovieDetailError.NetworkError
                )
            }
    }
}
