package com.github.filipelipan.moviesapp.movielist.data.mapper

import com.github.filipelipan.moviesapp.factories.response.MovieResponseFactory
import com.github.filipelipan.moviesapp.infraestructure.model.ImageUrl
import com.github.filipelipan.moviesapp.movielist.domain.model.Movie
import com.github.filipelipan.moviesapp.util.RandomUtil
import junit.framework.Assert.assertEquals
import org.junit.Test

class MoviesResponseToMoviesModelMapperDefaultTest {
    private val moviesResponseToMoviesModelMapper = MoviesResponseToMoviesModelMapperDefault()

    @Test
    fun mapFrom_response_returnMovies() {
        val movieResponse = MovieResponseFactory.make()
        val responses = listOf(movieResponse)

        val expected = Movie(
            id = movieResponse.id,
            name = movieResponse.title,
            imageUrl = ImageUrl.LowDefinitionImage(imagePath = movieResponse.poster_path),
            rating = movieResponse.vote_average,
        )

        val actual = moviesResponseToMoviesModelMapper.mapFrom(responses)

        assertEquals(listOf(expected), actual)
    }

    @Test
    fun mapFrom_asListOfResponses_returnSameAmountOfMovies() {
        val responses = RandomUtil.listOf { MovieResponseFactory.make() }

        val actual = moviesResponseToMoviesModelMapper.mapFrom(responses)

        assertEquals(responses.size, actual.size)
    }
}
