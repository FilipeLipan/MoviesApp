package com.github.filipelipan.moviesapp.movielist.data.repository

import com.github.filipelipan.moviesapp.infraestructure.model.Result
import com.github.filipelipan.moviesapp.movielist.data.datasource.LoadMoviesDataSource
import com.github.filipelipan.moviesapp.movielist.domain.error.LoadMoviesError
import com.github.filipelipan.moviesapp.movielist.domain.model.Movie
import com.github.filipelipan.moviesapp.movielist.domain.model.Pagination

class LoadMoviesRepositoryDefault(
    private val loadMoviesDataSource: LoadMoviesDataSource,
) : LoadMoviesRepository {
    override suspend fun loadMovies(
        language: String,
        page: String,
        loadType: String,
    ): Result<Pagination<Movie>, LoadMoviesError> {
        return loadMoviesDataSource.loadMovies(
            language = language,
            page = page,
            loadType = loadType,
        )
    }
}
