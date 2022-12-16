package com.github.filipelipan.moviesapp.moviedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.filipelipan.moviesapp.moviedetail.domain.model.MovieDetail
import com.github.filipelipan.moviesapp.moviedetail.domain.usecase.LoadMovieDetailUseCase
import com.github.filipelipan.moviesapp.navigation.MovieDestinationsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MovieDetailUiState(
    val isLoading: Boolean = false,
    val movieDetail: MovieDetail? = null,
    val showError: Boolean = false,
)

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val loadMovieDetailUseCase: LoadMovieDetailUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val movieId: String = savedStateHandle[MovieDestinationsArgs.MOVIE_ID_ARG]!!

    private val _showError = MutableStateFlow(false)
    private val _isLoading = MutableStateFlow(false)
    private val _movieDetail = MutableStateFlow<MovieDetail?>(null)

    val uiState: StateFlow<MovieDetailUiState> =
        combine(_isLoading, _movieDetail, _showError) { _isLoading, _movieDetail, _showError ->
            MovieDetailUiState(
                isLoading = _isLoading,
                movieDetail = _movieDetail,
                showError = _showError,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MovieDetailUiState()
        )

    init {
        loadMovieDetail()
    }

    fun loadMovieDetail() {
        viewModelScope.launch {
            _isLoading.value = true
            _showError.value = false
            loadMovieDetailUseCase(
                movieId = movieId
            ).onSuccess {
                _movieDetail.value = it
                _showError.value = false
                _isLoading.value = false
            }.onError {
                _showError.value = true
                _isLoading.value = false
            }
        }
    }

}
