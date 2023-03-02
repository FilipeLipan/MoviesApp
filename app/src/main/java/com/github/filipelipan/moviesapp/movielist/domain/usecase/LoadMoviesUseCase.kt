package com.github.filipelipan.moviesapp.movielist.domain.usecase

import com.github.filipelipan.moviesapp.movielist.domain.error.LoadMoviesError
import com.github.filipelipan.moviesapp.movielist.domain.model.Movie
import com.github.filipelipan.moviesapp.movielist.domain.model.Pagination
import com.github.filipelipan.moviesapp.infraestructure.model.Result

interface LoadMoviesUseCase {
    // handle language properly
    // add proper pagination
    suspend operator fun invoke(
        language: String = "en-US",
        page: String = "1",
        genreKey: String,
    ): Result<Pagination<Movie>, LoadMoviesError>
}
