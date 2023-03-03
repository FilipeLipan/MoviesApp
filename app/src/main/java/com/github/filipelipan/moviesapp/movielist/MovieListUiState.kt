package com.github.filipelipan.moviesapp.movielist

import com.github.filipelipan.moviesapp.movielist.domain.model.Movie

data class MovieListUiState(
    val isLoading: Boolean = true,
    val movies: List<Movie>? = null,
    val movieListScreenState: MovieListScreenState = MovieListScreenState.Success
)

sealed class MovieListAction {
    object Refresh : MovieListAction()
}

sealed class MovieListScreenState {
    object Error : MovieListScreenState()
    object Empty : MovieListScreenState()
    object Success : MovieListScreenState()
}
