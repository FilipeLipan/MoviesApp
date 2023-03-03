package com.github.filipelipan.moviesapp.moviedetail

import com.github.filipelipan.moviesapp.moviedetail.domain.model.MovieDetail

data class MovieDetailUiState(
    val isLoading: Boolean = true,
    val movieDetail: MovieDetail? = null,
    val movieDetailScreenState: MovieDetailScreenState = MovieDetailScreenState.Success
)

sealed class MovieDetailAction {
    object Refresh : MovieDetailAction()
}

sealed class MovieDetailScreenState {
    object Error : MovieDetailScreenState()
    object Empty : MovieDetailScreenState()
    object Success : MovieDetailScreenState()
}
