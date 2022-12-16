package com.github.filipelipan.moviesapp.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.filipelipan.moviesapp.movielist.domain.model.Movie
import com.github.filipelipan.moviesapp.movielist.domain.usecase.LoadMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MovieListUiState(
    val isLoading: Boolean = true,
    val movies: List<Movie>? = null,
    val showError: Boolean = false,
)

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val loadMoviesUseCase: LoadMoviesUseCase,
) : ViewModel() {

    private val _showError = MutableStateFlow(false)
    private val _isLoading = MutableStateFlow(false)
    private val _movies = MutableStateFlow<List<Movie>?>(null)

    val uiState: StateFlow<MovieListUiState> =
        combine(_isLoading, _movies, _showError) { _isLoading, _movies, _showError ->
            MovieListUiState(
                isLoading = _isLoading,
                movies = _movies,
                showError = _showError,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MovieListUiState()
        )

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            _showError.value = false
            loadMoviesUseCase(
                genreKey = "popular"
            ).onSuccess {
                _movies.value = it.movies
                _showError.value = false
                _isLoading.value = false
            }.onError {
                _showError.value = true
                _isLoading.value = false
            }
        }
    }

    fun refresh() {
        loadMovies()
    }
}
