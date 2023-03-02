package com.github.filipelipan.moviesapp.movielist.data.datasource

import com.github.filipelipan.moviesapp.movielist.domain.error.LoadMoviesError
import com.github.filipelipan.moviesapp.movielist.domain.model.Movie
import com.github.filipelipan.moviesapp.movielist.domain.model.Pagination
import com.github.filipelipan.moviesapp.infraestructure.coroutines.CoroutineDispatcherProvider
import com.github.filipelipan.moviesapp.infraestructure.coroutines.safeApiCall
import com.github.filipelipan.moviesapp.movielist.data.api.MovieListApi
import com.github.filipelipan.moviesapp.infraestructure.model.Result
import com.github.filipelipan.moviesapp.movielist.data.mapper.MoviesResponseToMoviesModelMapper

class LoadMoviesDataSourceDefault(
    private val movieApi: MovieListApi,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val moviesResponseToMoviesModelMapper: MoviesResponseToMoviesModelMapper,
) : LoadMoviesDataSource {

    override suspend fun loadMovies(
        language: String,
        page: String,
        loadType: String
    ): Result<Pagination<Movie>, LoadMoviesError> {

        return safeApiCall(coroutineDispatcherProvider.io()) {
            movieApi.loadMovies(
                language = language,
                page = page,
                loadType = loadType,
            )
        }.mapSuccess {
            return Result.Success(
                Pagination(
                    page = it.page,
                    totalPages = it.totalPages,
                    movies = moviesResponseToMoviesModelMapper.mapFrom(
                        it.movies
                    )
                )
            )
        }.mapError {
            return Result.Error(
                LoadMoviesError.NetworkError
            )
        }
    }
}
