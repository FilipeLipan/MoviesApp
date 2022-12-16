package com.github.filipelipan.moviesapp.moviedetail.data.mapper

import com.github.filipelipan.moviesapp.infraestructure.model.ImageUrl
import com.github.filipelipan.moviesapp.moviedetail.data.response.MovieDetailResponse
import com.github.filipelipan.moviesapp.moviedetail.domain.model.Genre
import com.github.filipelipan.moviesapp.moviedetail.domain.model.MovieDetail
import java.math.RoundingMode
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

const val RELEASE_DATE_PATTERN = "yyyy-MM-dd"
const val RELEASE_DATE_FORMAT_PATTERN = "MMM YYYY"

class MovieDetailResponseToMovieDetailMapperDefault() : MovieDetailResponseToMovieDetailMapper {
    override fun mapFrom(movieDetailResponse: MovieDetailResponse): MovieDetail {
        return MovieDetail(
            id = movieDetailResponse.id,
            name = movieDetailResponse.title,
            imageUrl = ImageUrl.HighDefinitionImage(
                imagePath = movieDetailResponse.poster_path
            ),
            genres = movieDetailResponse.genres.map { genreResponse ->
                Genre(
                    id = genreResponse.id,
                    name = genreResponse.name,
                )
            },
            overview = movieDetailResponse.overview,
            voteCount = movieDetailResponse.vote_count,
            description = formatDescription(
                movieDetailResponse.release_date,
                movieDetailResponse.vote_average
            )
        )
    }

    private fun formatDescription(
        releaseDateResponse: String,
        voteAverageResponse: Double
    ): String {
        val stringBuilder = StringBuilder()

        val formattedDate = releaseDateResponse.parseDateDefault()
            ?.formatDate(RELEASE_DATE_FORMAT_PATTERN)

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

fun String.parseDateDefault() : Date? {
    return try {
        val dateFormatter =  SimpleDateFormat(RELEASE_DATE_PATTERN, Locale.getDefault())
        dateFormatter.parse(this)
    } catch (parseException: ParseException) {
        null
    }
}

fun Date.formatDate(
    pattern: String
) : String? {
    return try {
        val dateFormatter =  SimpleDateFormat(pattern, Locale.getDefault())
        return dateFormatter.format(this)
    } catch (parseException: ParseException) {
        null
    }
}

fun Double.formatRating() : String {
    val decimal = this.toBigDecimal().setScale(1, RoundingMode.HALF_EVEN)
    return decimal.toString()
}