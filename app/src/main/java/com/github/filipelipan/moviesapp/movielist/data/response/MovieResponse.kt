package com.github.filipelipan.moviesapp.movielist.data.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieResponse(
    val id: Int,
    val title: String,
    val poster_path: String,
    val vote_average: String,
    val overview: String,
)