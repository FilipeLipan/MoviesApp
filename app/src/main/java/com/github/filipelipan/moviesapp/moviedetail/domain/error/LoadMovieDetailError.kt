package com.github.filipelipan.moviesapp.moviedetail.domain.error

sealed class LoadMovieDetailError {
    object NetworkError : LoadMovieDetailError()
    object LocalError : LoadMovieDetailError()
}