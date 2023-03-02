package com.github.filipelipan.moviesapp.moviedetail.data.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreResponse(
    val id: Int,
    val name: String,
)
