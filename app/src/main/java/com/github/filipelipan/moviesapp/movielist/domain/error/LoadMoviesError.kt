package com.github.filipelipan.moviesapp.movielist.domain.error

sealed class LoadMoviesError {
    object NetworkError : LoadMoviesError()
    object LocalError : LoadMoviesError()
}