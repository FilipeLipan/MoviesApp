package com.github.filipelipan.moviesapp.moviedetail.data.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailResponse(
    val id: Int,
    val title: String,
    val poster_path: String,
    val vote_average: Double,
    val vote_count: String,
    val overview: String,
    val genres: List<GenreResponse>,
    val release_date: String,
)
