package com.github.filipelipan.moviesapp.movielist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.github.filipelipan.moviesapp.R
import com.github.filipelipan.moviesapp.infraestructure.model.ImageUrl
import com.github.filipelipan.moviesapp.infraestructure.ui.GenericErrorScreen
import com.github.filipelipan.moviesapp.infraestructure.ui.LoadingContent
import com.github.filipelipan.moviesapp.movielist.domain.model.Movie
import com.github.filipelipan.moviesapp.ui.theme.MoviesAppTheme
import com.github.filipelipan.moviesapp.ui.theme.*

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
            onRefresh = { viewModel.refresh() },
            movies = uiState.movies.orEmpty(),
            loading = uiState.isLoading,
            error = uiState.showError,
        )
    }
}

@Composable
private fun MovieContent(
    loading: Boolean,
    error: Boolean,
    movies: List<Movie>,
    onRefresh: () -> Unit,
    onMovieItemClick: (Int) -> Unit,
) {
    LoadingContent(
        loading = loading,
        empty = movies.isEmpty() && !loading && !error,
        error = error,
        modifier = Modifier.wrapContentSize(),
        emptyContent = {
            GenericErrorScreen(
                title = stringResource(R.string.load_movie_empty_list),
                buttonName = stringResource(R.string.network_try_again),
                onButtonClick = onRefresh,
            )
        },
        errorContent = {
            GenericErrorScreen(
                title = stringResource(R.string.generic_network_error),
                buttonName = stringResource(R.string.network_try_again),
                onButtonClick = onRefresh,
            )
        },
        onRefresh = onRefresh
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
                items = movies,
                key = { movie -> movie.id }
            ) { movie ->
                MovieItem(
                    movie = movie,
                    onMovieItemClick = onMovieItemClick,
                )
            }
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

@Preview(showBackground = true)
@Composable
private fun MovieListScreenPreview() {
    MoviesAppTheme {
        MovieContent(
            loading = false,
            movies = listOf(
                Movie(2, "name", ImageUrl.LowDefinitionImage("sda"), "Viagem")
            ),
            onRefresh = { },
            onMovieItemClick = { },
            error = false
        )
    }
}
