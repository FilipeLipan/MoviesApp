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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.github.filipelipan.moviesapp.R
import com.github.filipelipan.moviesapp.infraestructure.ui.GenericEmptyScreen
import com.github.filipelipan.moviesapp.infraestructure.ui.GenericErrorScreen
import com.github.filipelipan.moviesapp.moviedetail.domain.model.MovieDetail
import com.github.filipelipan.moviesapp.ui.theme.Spacing
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun MovieDetailScreen(
    onBack: () -> Unit,
    viewModel: MovieDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MovieDetailScreenContent(
        onRefresh = { viewModel.dispatchViewAction(MovieDetailAction.Refresh) },
        movieDetail = uiState.movieDetail,
        isLoading = uiState.isLoading,
        movieDetailScreenState = uiState.movieDetailScreenState,
        onBack = onBack
    )
}

@Composable
fun MovieDetailScreenContent(
    isLoading: Boolean,
    movieDetail: MovieDetail?,
    onRefresh: () -> Unit,
    onBack: () -> Unit,
    movieDetailScreenState: MovieDetailScreenState
) {
    val content = when(movieDetailScreenState) {
        MovieDetailScreenState.Empty -> GenericEmptyScreen(
            title = stringResource(R.string.generic_empty_message),
            buttonName = stringResource(R.string.generic_empty_try_again_message),
            onButtonClick = onRefresh
        )
        MovieDetailScreenState.Error -> GenericErrorScreen(
            title = stringResource(R.string.generic_network_error_message),
            buttonName = stringResource(R.string.generic_network_error_try_again_message),
            onButtonClick = onRefresh
        )
        MovieDetailScreenState.Success -> MovieDetailScreenSuccessContent(
            movieDetail = movieDetail,
            onBack = onBack,
        )
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isLoading),
        onRefresh = onRefresh,
        modifier = Modifier.fillMaxSize(),
        content = { content }
    )
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
