package com.github.filipelipan.moviesapp.moviedetail

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.github.filipelipan.moviesapp.R
import com.github.filipelipan.moviesapp.infraestructure.model.ImageUrl
import com.github.filipelipan.moviesapp.infraestructure.ui.GenericErrorScreen
import com.github.filipelipan.moviesapp.infraestructure.ui.LoadingContent
import com.github.filipelipan.moviesapp.moviedetail.domain.model.Genre
import com.github.filipelipan.moviesapp.moviedetail.domain.model.MovieDetail
import com.github.filipelipan.moviesapp.ui.theme.MoviesAppTheme
import com.github.filipelipan.moviesapp.ui.theme.Spacing
import com.google.accompanist.flowlayout.FlowRow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun MovieDetailScreen(
    onBack: () -> Unit,
    viewModel: MovieDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MovieDetailScreenContent(
        onRefresh = { viewModel.loadMovieDetail() },
        movieDetail = uiState.movieDetail,
        loading = uiState.isLoading,
        error = uiState.showError,
        onBack = onBack
    )
}

@Composable
fun MovieDetailScreenContent(
    loading: Boolean,
    error: Boolean,
    movieDetail: MovieDetail?,
    onRefresh: () -> Unit,
    onBack: () -> Unit,
) {
    LoadingContent(
        loading = loading,
        empty = movieDetail == null && !loading && !error,
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
        MovieDetailScreenSuccessContent(
            movieDetail = movieDetail,
            onBack = onBack,
        )
    }
}

private const val CARD_CORNER_SIZE = 50

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreenSuccessContent(
    movieDetail: MovieDetail?,
    onBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Surface(
            shadowElevation = 9.dp, // play with the elevation values
        ) {
            ConstraintLayout(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(9.dp))
            ) {

                val (headerImage, posterImage, title, description, backButton, bottomSpace) = createRefs()

                AsyncImage(
                    model = movieDetail?.imageUrl?.getFullUrl(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(234.dp)
                        .constrainAs(headerImage) {
                            top.linkTo(parent.top)
                        },
                    contentScale = ContentScale.FillWidth
                )

                FloatingActionButton(
                    modifier = Modifier.constrainAs(backButton) {
                        top.linkTo(parent.top, margin = Spacing.level3)
                        start.linkTo(parent.start, margin = Spacing.level3)
                    },
                    onClick = { onBack.invoke() },
                    shape = RoundedCornerShape(CARD_CORNER_SIZE)
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                }

                Card(
                    modifier = Modifier
                        .width(104.dp)
                        .height(156.dp)
                        .constrainAs(posterImage) {
                            start.linkTo(parent.start, margin = Spacing.level5)
                            top.linkTo(headerImage.bottom)
                            bottom.linkTo(headerImage.bottom)
                        },
                    elevation = CardDefaults.elevatedCardElevation(),
                ) {

                    // TODO add error and placeholder image
                    AsyncImage(
                        model = movieDetail?.imageUrl?.getFullUrl(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }

                Text(
                    text = movieDetail?.name.orEmpty(),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .wrapContentHeight()
                        .constrainAs(title) {
                            width = Dimension.fillToConstraints
                            start.linkTo(posterImage.end, margin = Spacing.level5)
                            end.linkTo(parent.end, margin = Spacing.level5)
                            top.linkTo(headerImage.bottom, margin = Spacing.level3)
                        },
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    text = movieDetail?.description.orEmpty(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier
                        .wrapContentHeight()
                        .constrainAs(description) {
                            width = Dimension.fillToConstraints
                            start.linkTo(posterImage.end, margin = Spacing.level5)
                            end.linkTo(parent.end, margin = Spacing.level5)
                            top.linkTo(title.bottom, margin = Spacing.level2)
                        },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier
                    .height(Spacing.level9)
                    .constrainAs(bottomSpace) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(posterImage.bottom)
                    }
                )
            }
        }

        val defaultSpacing = Spacing.level5

        Spacer(modifier = Modifier.height(Spacing.level7))

        Text(
            text = movieDetail?.overview.orEmpty(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = defaultSpacing, end = defaultSpacing)
        )

        Spacer(modifier = Modifier.height(Spacing.level5))

        Text(
            text = stringResource(R.string.movie_detail_genres_title),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = defaultSpacing, end = defaultSpacing)
        )

        Spacer(modifier = Modifier.height(Spacing.level4))

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = defaultSpacing, end = defaultSpacing),
            mainAxisSpacing = Spacing.level2,
        ) {

            movieDetail?.genres?.forEach {
                AssistChip(
                    modifier = Modifier,
                    onClick = { },
                    label = { Text(text = it.name) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieListScreenPreview() {
    MoviesAppTheme {
        MovieDetailScreenContent(
            onRefresh = { },
            movieDetail = MovieDetail(
                id = 123,
                name = "23232",
                imageUrl = ImageUrl.HighDefinitionImage(
                    imagePath = "/pFlaoHTZeyNkG83vxsAJiGzfSsa.jpg"
                ),
                genres = listOf(
                    Genre(1, "Ação"),
                    Genre(2, "Aventura"),
                    Genre(3, "Aventura"),
                    Genre(4, "Aventura"),
                    Genre(5, "Aventura"),
                ),
                overview = "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes",
                voteCount = "23232",
                description = "Feb 2022 - 203 mins"
            ),
            loading = false,
            error = false,
            onBack = { }
        )
    }
}
