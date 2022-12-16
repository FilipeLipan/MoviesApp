package com.github.filipelipan.moviesapp.movielist.domain.model

data class Pagination<T>(
    val page: Int,
    val totalPages: Int,
    val movies: List<T>,
)
