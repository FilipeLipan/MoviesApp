package com.github.filipelipan.moviesapp.movielist.data.datasource

import com.github.filipelipan.moviesapp.factories.model.HttpExceptionFactory
import com.github.filipelipan.moviesapp.factories.model.MovieFactory
import com.github.filipelipan.moviesapp.factories.response.MoviePagingResponseFactory
import com.github.filipelipan.moviesapp.infraestructure.model.Result
import com.github.filipelipan.moviesapp.movielist.data.api.MovieListApi
import com.github.filipelipan.moviesapp.movielist.data.mapper.MoviesResponseToMoviesModelMapper
import com.github.filipelipan.moviesapp.movielist.data.response.MoviePagingResponse
import com.github.filipelipan.moviesapp.movielist.domain.error.LoadMoviesError
import com.github.filipelipan.moviesapp.movielist.domain.model.Movie
import com.github.filipelipan.moviesapp.movielist.domain.model.Pagination
import com.github.filipelipan.moviesapp.util.CoroutinesRule
import com.github.filipelipan.moviesapp.util.RandomUtil
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class LoadMoviesDataSourceDefaultTest {

    @get:Rule
    val coroutinesRule = CoroutinesRule()

    private val movieListApi: MovieListApi = mockk()
    private val moviesResponseToMoviesModelMapper: MoviesResponseToMoviesModelMapper = mockk()

    private val loadMoviesDataSourceDefault = LoadMoviesDataSourceDefault(
        movieApi = movieListApi,
        coroutineDispatcherProvider = coroutinesRule.dispatcherProvider,
        moviesResponseToMoviesModelMapper = moviesResponseToMoviesModelMapper,
    )

    @Test
    fun loadMovies_withConnectionError_returnError() = runTest {
        prepareScenario(
            loadMovieException = HttpExceptionFactory.make()
        )

        val actual = loadMoviesDataSourceDefault.loadMovies("", "", "")

        assertEquals(Result.Error(LoadMoviesError.NetworkError), actual)
    }

    @Test
    fun loadMovies_withSuccess_invokeApiWithGivenParams() = runTest {
        prepareScenario()
        val language = RandomUtil.name()
        val page = RandomUtil.int().toString()
        val loadType = RandomUtil.name()

        loadMoviesDataSourceDefault.loadMovies(
            language = language,
            page = page,
            loadType = loadType,
        )

        coVerify {
            movieListApi.loadMovies(
                language = language,
                page = page,
                loadType = loadType,
            )
        }
    }

    @Test
    fun loadMovies_withSuccess_invokeMapperWithGivenParams() = runTest {
        val loadMovieResponse = MoviePagingResponseFactory.make()
        prepareScenario(loadMovieResponse = loadMovieResponse)

        loadMoviesDataSourceDefault.loadMovies("", "", "")

        coVerify {
            moviesResponseToMoviesModelMapper.mapFrom(loadMovieResponse.movies)
        }
    }

    @Test
    fun loadMovies_withSuccess_returnMappedValue() = runTest {
        val loadMovieResponse = MoviePagingResponseFactory.make()
        val loadMovieMapperModel = RandomUtil.listOf { MovieFactory.make() }
        prepareScenario(
            loadMovieResponse = loadMovieResponse,
            loadMovieMapperModel = loadMovieMapperModel,
        )

        val actual = loadMoviesDataSourceDefault.loadMovies("", "", "")

        val expected = Result.Success(
            Pagination(
                page = loadMovieResponse.page,
                totalPages = loadMovieResponse.total_pages,
                movies = loadMovieMapperModel
            )
        )

        assertEquals(expected, actual)
    }

    private fun prepareScenario(
        loadMovieResponse: MoviePagingResponse = MoviePagingResponseFactory.make(),
        loadMovieException: Exception? = null,
        loadMovieMapperModel: List<Movie> = RandomUtil.listOf { MovieFactory.make() },
    ) {
        if (loadMovieException == null) {
            coEvery {
                movieListApi.loadMovies(any(), any(), any())
            } returns loadMovieResponse
        } else {
            coEvery {
                movieListApi.loadMovies(any(), any(), any())
            } throws loadMovieException
        }

        every {
            moviesResponseToMoviesModelMapper.mapFrom(any())
        } returns loadMovieMapperModel
    }
}
