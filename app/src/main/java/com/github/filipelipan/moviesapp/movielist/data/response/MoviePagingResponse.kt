package com.github.filipelipan.moviesapp.movielist.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoviePagingResponse(
    val page: Int,
    val total_pages: Int,
    @Json(name = "results")
    val movies: List<MovieResponse>,
)