package com.github.filipelipan.moviesapp.movielist.data.mapper

import com.github.filipelipan.moviesapp.infraestructure.model.ImageUrl
import com.github.filipelipan.moviesapp.movielist.data.response.MovieResponse
import com.github.filipelipan.moviesapp.movielist.domain.model.Movie

class MoviesResponseToMoviesModelMapperDefault : MoviesResponseToMoviesModelMapper {
    override fun mapFrom(
        movies: List<MovieResponse>
    ): List<Movie> {
        return movies.map {
            Movie(
                id = it.id,
                name = it.title,
                imageUrl = ImageUrl.LowDefinitionImage(imagePath = it.poster_path),
                rating = it.vote_average,
            )
        }
    }
}
