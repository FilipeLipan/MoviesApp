package com.github.filipelipan.moviesapp.movielist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.github.filipelipan.moviesapp.R
import com.github.filipelipan.moviesapp.infraestructure.ui.GenericEmptyScreen
import com.github.filipelipan.moviesapp.infraestructure.ui.GenericErrorScreen
import com.github.filipelipan.moviesapp.movielist.domain.model.Movie
import com.github.filipelipan.moviesapp.ui.theme.Spacing
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun MovieListScreen(
    onMovieItemClick: (Int) -> Unit,
    viewModel: MovieListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        MovieContent(
            onMovieItemClick = onMovieItemClick,
            onRefresh = { viewModel.dispatchViewAction(MovieListAction.Refresh) },
            movies = uiState.movies.orEmpty(),
            isLoading = uiState.isLoading,
            movieListScreenState = uiState.movieListScreenState
        )
    }
}

@Composable
private fun MovieContent(
    isLoading: Boolean,
    movieListScreenState: MovieListScreenState,
    movies: List<Movie>?,
    onMovieItemClick: (Int) -> Unit,
    onRefresh: () -> Unit,
) {

    val content = when (movieListScreenState) {
        MovieListScreenState.Empty -> GenericEmptyScreen(
            title = stringResource(R.string.generic_empty_message),
            buttonName = stringResource(R.string.generic_empty_try_again_message),
            buttonAction = onRefresh
        )
        MovieListScreenState.Error -> GenericErrorScreen(
            title = stringResource(R.string.generic_network_error_message),
            buttonName = stringResource(R.string.generic_network_error_try_again_message),
            buttonAction = onRefresh
        )
        MovieListScreenState.Success -> MovieListScreenSuccessContent(
            movies = movies,
            onMovieItemClick = onMovieItemClick,
        )
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isLoading),
        onRefresh = onRefresh,
        modifier = Modifier.fillMaxSize(),
        content = { content }
    )
}

@Composable
private fun MovieListScreenSuccessContent(
    movies: List<Movie>?,
    onMovieItemClick: (Int) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Adaptive(minSize = 116.dp),
        contentPadding = PaddingValues(
            start = Spacing.level4,
            end = Spacing.level4,
            top = Spacing.level5,
            bottom = Spacing.level5,
        ),
    ) {
        items(
            items = movies.orEmpty(),
            key = { movie -> movie.id }
        ) { movie ->
            MovieItem(
                movie = movie,
                onMovieItemClick = onMovieItemClick,
            )
        }
    }
}

@Composable
private fun MovieItem(
    movie: Movie,
    onMovieItemClick: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .width(100.dp)
            .padding(Spacing.level2)
            .clickable { onMovieItemClick.invoke(movie.id) },
        elevation = CardDefaults.elevatedCardElevation(),
    ) {
        // TODO add error and placeholder image
        AsyncImage(
            model = movie.imageUrl.getFullUrl(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
