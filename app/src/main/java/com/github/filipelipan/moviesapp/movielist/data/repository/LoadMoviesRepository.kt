package com.github.filipelipan.moviesapp.movielist.data.repository

import com.github.filipelipan.moviesapp.infraestructure.model.Result
import com.github.filipelipan.moviesapp.movielist.domain.error.LoadMoviesError
import com.github.filipelipan.moviesapp.movielist.domain.model.Movie
import com.github.filipelipan.moviesapp.movielist.domain.model.Pagination

interface LoadMoviesRepository {
    suspend fun loadMovies(
        language: String,
        page: String,
        loadType: String,
    ): Result<Pagination<Movie>, LoadMoviesError>
}
