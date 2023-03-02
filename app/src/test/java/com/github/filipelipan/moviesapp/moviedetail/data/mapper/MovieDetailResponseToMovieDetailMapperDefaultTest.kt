package com.github.filipelipan.moviesapp.moviedetail.data.mapper

import com.github.filipelipan.moviesapp.factories.response.MovieDetailResponseFactory
import com.github.filipelipan.moviesapp.infraestructure.model.ImageUrl
import com.github.filipelipan.moviesapp.moviedetail.domain.model.Genre
import com.github.filipelipan.moviesapp.moviedetail.domain.model.MovieDetail
import junit.framework.Assert.assertEquals
import org.junit.Test
import java.math.RoundingMode

class MovieDetailResponseToMovieDetailMapperDefaultTest {

    private val subject = MovieDetailResponseToMovieDetailMapperDefault()

    @Test
    fun mapFrom_response_returnMovieDetails() {
        val response = MovieDetailResponseFactory.make(
            releaseDate = "2022-10-19"
        )
        val rating = response.voteAverage
            .toBigDecimal().setScale(1, RoundingMode.HALF_EVEN)
        val actual = subject.mapFrom(response)

        val expected = MovieDetail(
            id = response.id,
            name = response.title,
            imageUrl = ImageUrl.HighDefinitionImage(
                imagePath = response.posterPath
            ),
            genres = response.genres.map { genreResponse ->
                Genre(
                    id = genreResponse.id,
                    name = genreResponse.name,
                )
            },
            overview = response.overview,
            voteCount = response.voteCount,
            description = "out 2022 - $rating/10"
        )

        assertEquals(expected, actual)
    }

    @Test
    fun mapFromResponse_withoutValidDate_returnDescriptionWithoutDate() {
        val response = MovieDetailResponseFactory.make(
            releaseDate = "dwqdwq"
        )
        val rating = response.voteAverage
            .toBigDecimal().setScale(1, RoundingMode.HALF_EVEN)
        val actual = subject.mapFrom(response)

        val expected = "$rating/10"

        assertEquals(expected, actual.description)
    }
}
