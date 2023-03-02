package com.github.filipelipan.moviesapp.moviedetail.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailResponse(
    val id: Int,
    val title: String,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: String,
    val overview: String,
    val genres: List<GenreResponse>,
    @Json(name = "release_date")
    val releaseDate: String,
)
