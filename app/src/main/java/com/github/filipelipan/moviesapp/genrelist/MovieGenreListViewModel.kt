package com.github.filipelipan.moviesapp.genrelist

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

data class MovieGenreListUiState(
    val isLoading: Boolean = false,
    val movies: List<Movie>? = null
)

private const val STOP_TIMEOUT = 5000L

@HiltViewModel
class MovieGenreListViewModel @Inject constructor(
    val loadMoviesUseCase: LoadMoviesUseCase,
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    private val _movies = MutableStateFlow<List<Movie>?>(null)

    val uiState: StateFlow<MovieGenreListUiState> =
        combine(_isLoading, _movies) { _isLoading, _movies ->
            MovieGenreListUiState(
                isLoading = _isLoading,
                movies = _movies
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT),
            initialValue = MovieGenreListUiState()
        )

    fun loadMovies(genreKey: String) {
        viewModelScope.launch {
            _isLoading.value = true
            loadMoviesUseCase(
                genreKey = genreKey
            ).onSuccess {
                _movies.value = it.movies
                _isLoading.value = false
            }.onError {
                _isLoading.value = false
            }
        }
    }
}
