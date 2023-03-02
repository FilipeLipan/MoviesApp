package com.github.filipelipan.moviesapp.genrelist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.filipelipan.moviesapp.movielist.domain.model.Movie
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import coil.compose.AsyncImage
import com.github.filipelipan.moviesapp.ui.theme.Spacing

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun MovieCategoryComposable(
    genre: String,
    genreKey: String,
    onMovieItemClick: (Int) -> Unit,
    // TODO create multiple instances with hilt
    viewModel: MovieGenreListViewModel = hiltViewModel(),
) {
    TODO("under development, don't use")

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // TODO send genreKey to the viewmodel, to avoid multiple recomposes
    viewModel.loadMovies(genreKey)

    MovieCategoryContent(
        genre = genre,
        onMovieItemClick = onMovieItemClick,
        movies = uiState.movies.orEmpty(),
    )
}

@Composable
private fun MovieCategoryContent(
    genre: String,
    movies: List<Movie>,
    onMovieItemClick: (Int) -> Unit,
) {

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.Cyan)
    ) {

        Text(
            style = MaterialTheme.typography.headlineMedium,
            text = genre,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight().background(Color.Green)
                .padding(
                    start = Spacing.level5,
                    top = Spacing.level2
                ),
        )

        LazyRow(
            contentPadding = PaddingValues(start = Spacing.level5, end = Spacing.level5),
            horizontalArrangement = Arrangement.spacedBy(Spacing.level3),
            modifier = Modifier
                .wrapContentHeight()
                .background(Color.Magenta)
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
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .width(100.dp)
            .clickable { onMovieItemClick.invoke(movie.id) }
    ) {

        AsyncImage(
            model = movie.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
        )

        Text(
            text = movie.name,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            maxLines = 1,
        )

        Text(
            text = movie.rating,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            maxLines = 1,
        )
    }
}
