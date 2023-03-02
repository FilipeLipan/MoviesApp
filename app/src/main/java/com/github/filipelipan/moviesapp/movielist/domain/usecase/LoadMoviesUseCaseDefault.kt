package com.github.filipelipan.moviesapp.movielist.domain.usecase

import com.github.filipelipan.moviesapp.movielist.domain.error.LoadMoviesError
import com.github.filipelipan.moviesapp.movielist.domain.model.Movie
import com.github.filipelipan.moviesapp.movielist.domain.model.Pagination
import com.github.filipelipan.moviesapp.infraestructure.model.Result
import com.github.filipelipan.moviesapp.movielist.data.repository.LoadMoviesRepository

class LoadMoviesUseCaseDefault(
    private val loadMoviesRepository: LoadMoviesRepository
) : LoadMoviesUseCase {

    override suspend fun invoke(
        language: String,
        page: String,
        genreKey: String,
    ): Result<Pagination<Movie>, LoadMoviesError> {
        return loadMoviesRepository.loadMovies(
            language = language,
            page = page,
            loadType = genreKey
        )
    }
}
