package com.github.filipelipan.moviesapp.movielist.data.mapper

import com.github.filipelipan.moviesapp.movielist.data.response.MovieResponse
import com.github.filipelipan.moviesapp.movielist.domain.model.Movie

interface MoviesResponseToMoviesModelMapper {
    fun mapFrom(
        movies: List<MovieResponse>
    ): List<Movie>
}
