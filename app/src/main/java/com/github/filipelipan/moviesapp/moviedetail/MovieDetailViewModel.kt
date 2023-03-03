package com.github.filipelipan.moviesapp.moviedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.filipelipan.moviesapp.infraestructure.ui.ActionDispatcher
import com.github.filipelipan.moviesapp.moviedetail.domain.model.MovieDetail
import com.github.filipelipan.moviesapp.moviedetail.domain.usecase.LoadMovieDetailUseCase
import com.github.filipelipan.moviesapp.navigation.AppDestinationsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val STOP_TIMEOUT = 5000L

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val loadMovieDetailUseCase: LoadMovieDetailUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), ActionDispatcher<StateFlow<MovieDetailUiState>, MovieDetailAction> {

    private val movieId: String = savedStateHandle[AppDestinationsArgs.MOVIE_ID_ARG]!!

    private val _isLoading = MutableStateFlow(false)
    private val _movieDetail = MutableStateFlow<MovieDetail?>(null)
    private val _movieListScreenState = MutableStateFlow<MovieDetailScreenState>(MovieDetailScreenState.Success)

    override val uiState: StateFlow<MovieDetailUiState> =
        combine(_isLoading, _movieDetail, _movieListScreenState) {
                _isLoading, _movieDetail, _movieListScreenState ->
            MovieDetailUiState(
                isLoading = _isLoading,
                movieDetail = _movieDetail,
                movieDetailScreenState = _movieListScreenState
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT),
            initialValue = MovieDetailUiState()
        )

    override fun dispatchViewAction(viewAction: MovieDetailAction) {
        when (viewAction) {
            MovieDetailAction.Refresh -> loadMovieDetail()
        }
    }

    init {
        loadMovieDetail()
    }

    private fun loadMovieDetail() {
        viewModelScope.launch {
            _isLoading.value = true
            loadMovieDetailUseCase(
                movieId = movieId
            ).onSuccess {
                _movieDetail.value = it
                _movieListScreenState.value = MovieDetailScreenState.Success
                _isLoading.value = false
            }.onError {
                _movieListScreenState.value = MovieDetailScreenState.Error
                _isLoading.value = false
            }
        }
    }
}
