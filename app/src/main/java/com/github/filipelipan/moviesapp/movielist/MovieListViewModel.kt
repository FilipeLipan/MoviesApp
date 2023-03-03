package com.github.filipelipan.moviesapp.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.filipelipan.moviesapp.movielist.domain.model.Movie
import com.github.filipelipan.moviesapp.movielist.domain.usecase.LoadMoviesUseCase
import com.github.filipelipan.moviesapp.infraestructure.ui.ActionDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch
import javax.inject.Inject

const val STOP_TIMEOUT = 5000L

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val loadMoviesUseCase: LoadMoviesUseCase,
) : ViewModel(), ActionDispatcher<StateFlow<MovieListUiState>, MovieListAction> {

    private val _isLoading = MutableStateFlow(false)
    private val _movies = MutableStateFlow<List<Movie>?>(null)
    private val _movieListScreenState = MutableStateFlow<MovieListScreenState>(MovieListScreenState.Success)
    init {
        loadMovies()
    }

    override val uiState: StateFlow<MovieListUiState> =
        combine(_isLoading, _movies, _movieListScreenState) { _isLoading, _movies, _homeScreenState ->
            MovieListUiState(
                isLoading = _isLoading,
                movies = _movies,
                movieListScreenState = _homeScreenState
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT),
            initialValue = MovieListUiState()
        )

    override fun dispatchViewAction(viewAction: MovieListAction) {
        when(viewAction) {
            MovieListAction.Refresh -> refresh()
        }
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            loadMoviesUseCase(
                genreKey = "popular"
            ).onSuccess {
                if (it.movies.isNotEmpty()) {
                    _movies.value = it.movies
                    _movieListScreenState.value = MovieListScreenState.Success
                } else {
                    _movies.value = emptyList()
                    _movieListScreenState.value = MovieListScreenState.Empty
                }
                _isLoading.value = false
            }.onError {
                _movies.value = null
                _isLoading.value = false
                _movieListScreenState.value = MovieListScreenState.Error
            }
        }
    }

    private fun refresh() {
        loadMovies()
    }
}
