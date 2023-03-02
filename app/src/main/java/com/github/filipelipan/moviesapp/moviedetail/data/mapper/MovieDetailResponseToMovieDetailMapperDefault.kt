package com.github.filipelipan.moviesapp.moviedetail.data.mapper

import com.github.filipelipan.moviesapp.infraestructure.model.ImageUrl
import com.github.filipelipan.moviesapp.infraestructure.util.formatDate
import com.github.filipelipan.moviesapp.infraestructure.util.formatRating
import com.github.filipelipan.moviesapp.infraestructure.util.parseDateDefault
import com.github.filipelipan.moviesapp.moviedetail.data.response.MovieDetailResponse
import com.github.filipelipan.moviesapp.moviedetail.domain.model.Genre
import com.github.filipelipan.moviesapp.moviedetail.domain.model.MovieDetail
import java.util.Locale

const val RELEASE_DATE_FORMAT_PATTERN = "MMM YYYY"

class MovieDetailResponseToMovieDetailMapperDefault : MovieDetailResponseToMovieDetailMapper {
    override fun mapFrom(
        movieDetailResponse: MovieDetailResponse,
        locale: Locale
    ): MovieDetail {
        return MovieDetail(
            id = movieDetailResponse.id,
            name = movieDetailResponse.title,
            imageUrl = ImageUrl.HighDefinitionImage(
                imagePath = movieDetailResponse.posterPath
            ),
            genres = movieDetailResponse.genres.map { genreResponse ->
                Genre(
                    id = genreResponse.id,
                    name = genreResponse.name,
                )
            },
            overview = movieDetailResponse.overview,
            voteCount = movieDetailResponse.voteCount,
            description = formatDescription(
                movieDetailResponse.releaseDate,
                movieDetailResponse.voteAverage,
                locale
            )
        )
    }

    private fun formatDescription(
        releaseDateResponse: String,
        voteAverageResponse: Double,
        locale: Locale
    ): String {
        val stringBuilder = StringBuilder()

        val formattedDate = releaseDateResponse.parseDateDefault(locale)
            ?.formatDate(RELEASE_DATE_FORMAT_PATTERN, locale)

        val formattedRating = voteAverageResponse
            .formatRating()
            .plus("/10")

        formattedDate?.let {
            stringBuilder
                .append(formattedDate)
                .append(" - ")
        }
        stringBuilder.append(formattedRating)

        return stringBuilder.toString()
    }
}
