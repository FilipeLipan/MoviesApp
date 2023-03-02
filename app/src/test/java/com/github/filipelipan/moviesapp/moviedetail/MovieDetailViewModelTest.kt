package com.github.filipelipan.moviesapp.moviedetail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.github.filipelipan.moviesapp.factories.model.MovieDetailFactory
import com.github.filipelipan.moviesapp.infraestructure.model.Result
import com.github.filipelipan.moviesapp.moviedetail.domain.error.LoadMovieDetailError
import com.github.filipelipan.moviesapp.moviedetail.domain.model.MovieDetail
import com.github.filipelipan.moviesapp.moviedetail.domain.usecase.LoadMovieDetailUseCase
import com.github.filipelipan.moviesapp.navigation.AppDestinationsArgs
import com.github.filipelipan.moviesapp.util.RandomUtil
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class MovieDetailViewModelTest {
    private val loadMovieDetailUseCase: LoadMovieDetailUseCase = mockk()
    private lateinit var movieDetailViewModel: MovieDetailViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadMovieDetail_withSuccess_callUseCaseWithGivenParams() = runTest {
        val movieId = RandomUtil.id().toString()
        prepareScenario(movieId = movieId)

        coVerify {
            loadMovieDetailUseCase(movieId)
        }
    }

    @Test
    fun loadMovieDetail_withSuccess_showSuccess() = runTest {
        val movieDetail = MovieDetailFactory.make()
        prepareScenario(
            loadMoviesResult = Result.Success(movieDetail)
        )

        val expected = MovieDetailUiState(
            isLoading = false,
            showError = false,
            movieDetail = movieDetail
        )

        movieDetailViewModel.uiState.test {
            assertEquals(expected, awaitItem())
        }
    }


    @Test
    fun loadMovieDetail_withError_showError() = runTest {
        prepareScenario(
            loadMoviesResult = Result.Error(LoadMovieDetailError.NetworkError)
        )

        val expected = MovieDetailUiState(
            isLoading = false,
            showError = true,
            movieDetail = null
        )

        movieDetailViewModel.uiState.test {
            assertEquals(expected, awaitItem())
        }
    }

    private fun prepareScenario(
        loadMoviesResult: Result<MovieDetail, LoadMovieDetailError> =
            Result.Success(MovieDetailFactory.make()),
        movieId: String = RandomUtil.id().toString()
    ) {
        coEvery {
            loadMovieDetailUseCase(any())
        } returns loadMoviesResult

        movieDetailViewModel = MovieDetailViewModel(
            loadMovieDetailUseCase = loadMovieDetailUseCase,
            savedStateHandle = SavedStateHandle(mapOf(AppDestinationsArgs.MOVIE_ID_ARG to movieId))
        )
    }
}
