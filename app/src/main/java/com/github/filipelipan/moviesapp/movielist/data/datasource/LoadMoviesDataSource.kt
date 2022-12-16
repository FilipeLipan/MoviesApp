package com.github.filipelipan.moviesapp.movielist.data.datasource

import com.github.filipelipan.moviesapp.movielist.domain.error.LoadMoviesError
import com.github.filipelipan.moviesapp.movielist.domain.model.Movie
import com.github.filipelipan.moviesapp.movielist.domain.model.Pagination
import com.github.filipelipan.moviesapp.infraestructure.model.Result

interface LoadMoviesDataSource {
    suspend fun loadMovies(
        language: String,
        page: String,
        loadType: String,
    ): Result<Pagination<Movie>, LoadMoviesError>
}
