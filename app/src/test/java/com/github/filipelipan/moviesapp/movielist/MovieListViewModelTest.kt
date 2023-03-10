package com.github.filipelipan.moviesapp.movielist

import app.cash.turbine.test
import com.github.filipelipan.moviesapp.factories.model.MovieFactory
import com.github.filipelipan.moviesapp.factories.model.PagingFactory
import com.github.filipelipan.moviesapp.infraestructure.model.Result
import com.github.filipelipan.moviesapp.movielist.domain.error.LoadMoviesError
import com.github.filipelipan.moviesapp.movielist.domain.model.Movie
import com.github.filipelipan.moviesapp.movielist.domain.model.Pagination
import com.github.filipelipan.moviesapp.movielist.domain.usecase.LoadMoviesUseCase
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

class MovieListViewModelTest {

    private val loadMoviesUseCase: LoadMoviesUseCase = mockk()
    private lateinit var movieListViewModel: MovieListViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadMovies_withSuccess_showSuccess() = runTest {
        val movies = RandomUtil.listOf { MovieFactory.make() }
        prepareScenario(
            loadMoviesResult = Result.Success(PagingFactory.make(movies = movies))
        )

        val expected = MovieListUiState(
            isLoading = false,
            movies = movies,
            movieListScreenState = MovieListScreenState.Success
        )

        movieListViewModel.uiState.test {
            assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun loadMovies_withSuccessWithEmptyList_showEmpty() = runTest {
        val movies = emptyList<Movie>()
        prepareScenario(
            loadMoviesResult = Result.Success(PagingFactory.make(movies = movies))
        )

        val expected = MovieListUiState(
            isLoading = false,
            movies = movies,
            movieListScreenState = MovieListScreenState.Empty
        )

        movieListViewModel.uiState.test {
            assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun refreshMovies_withSuccess_reloadMovies() = runTest {
        val movies = RandomUtil.listOf { MovieFactory.make() }
        prepareScenario(
            loadMoviesResult = Result.Success(PagingFactory.make(movies = movies))
        )

        movieListViewModel.dispatchViewAction(MovieListAction.Refresh)

        coVerify(exactly = 2) {
            loadMoviesUseCase(genreKey = any())
        }
    }

    @Test
    fun loadMovies_withError_showError() = runTest {
        prepareScenario(
            loadMoviesResult = Result.Error(LoadMoviesError.NetworkError)
        )

        val expected = MovieListUiState(
            isLoading = false,
            movies = null,
            movieListScreenState = MovieListScreenState.Error
        )

        movieListViewModel.uiState.test {
            assertEquals(expected, awaitItem())
        }
    }


    private fun prepareScenario(
        loadMoviesResult: Result<Pagination<Movie>, LoadMoviesError> =
            Result.Success(PagingFactory.make())
    ) {
        coEvery {
            loadMoviesUseCase(genreKey = any())
        } returns loadMoviesResult

        movieListViewModel = MovieListViewModel(loadMoviesUseCase = loadMoviesUseCase)
    }
}
