package com.github.filipelipan.moviesapp.moviedetail.data.datasource

import com.github.filipelipan.moviesapp.util.CoroutinesRule
import com.github.filipelipan.moviesapp.factories.model.HttpExceptionFactory
import com.github.filipelipan.moviesapp.factories.model.MovieDetailFactory
import com.github.filipelipan.moviesapp.factories.response.MovieDetailResponseFactory
import com.github.filipelipan.moviesapp.infraestructure.model.Result
import com.github.filipelipan.moviesapp.moviedetail.data.api.MovieDetailApi
import com.github.filipelipan.moviesapp.moviedetail.data.mapper.MovieDetailResponseToMovieDetailMapper
import com.github.filipelipan.moviesapp.moviedetail.data.response.MovieDetailResponse
import com.github.filipelipan.moviesapp.moviedetail.domain.error.LoadMovieDetailError
import com.github.filipelipan.moviesapp.moviedetail.domain.model.MovieDetail
import com.github.filipelipan.moviesapp.util.RandomUtil
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class LoadMovieDetailDataSourceTest {

    @get:Rule
    val coroutinesRule = CoroutinesRule()

    private val movieDetailApi: MovieDetailApi = mockk()
    private val movieDetailResponseToMovieDetailMapper: MovieDetailResponseToMovieDetailMapper =
        mockk()

    private val loadMovieDetailDataSource = LoadMovieDetailDataSourceDefault(
        movieDetailApi = movieDetailApi,
        coroutineDispatcherProvider = coroutinesRule.dispatcherProvider,
        movieDetailResponseToMovieDetailMapper = movieDetailResponseToMovieDetailMapper,
    )

    @Test
    fun loadMovieDetail_withConnectionError_returnError() = runTest {
        prepareScenario(
            movieDetailException = HttpExceptionFactory.make()
        )
        val movieId = RandomUtil.id().toString()

        val actual = loadMovieDetailDataSource.loadMovieDetail(
            movieId = movieId,
            language = ""
        )

        assertEquals(
            Result.Error(
                LoadMovieDetailError.NetworkError
            ),
            actual
        )
    }

    @Test
    fun loadMovieDetail_withSuccess_invokeApiWithGivenParams() = runTest {
        prepareScenario()
        val movieId = RandomUtil.id().toString()
        val language = "en-US"

        loadMovieDetailDataSource.loadMovieDetail(
            movieId = movieId,
            language = language,
        )

        coVerify {
            movieDetailApi.loadMovieDetails(
                movieId = movieId,
                language = "en-US",
            )
        }
    }

    @Test
    fun loadMovieDetail_withSuccess_invokeMapperWithGivenParams() = runTest {
        val movieDetailResponse = MovieDetailResponseFactory.make()
        prepareScenario(movieDetailResponse = movieDetailResponse)
        val movieId = RandomUtil.id().toString()

        loadMovieDetailDataSource.loadMovieDetail(
            movieId = movieId,
            language = "",
        )

        coVerify {
            movieDetailResponseToMovieDetailMapper.mapFrom(movieDetailResponse)
        }
    }

    @Test
    fun loadMovieDetail_withSuccess_returnMappedValue() = runTest {
        val movieDetailMapperResponse = MovieDetailFactory.make()
        prepareScenario(
            movieDetailMapperModel = movieDetailMapperResponse
        )
        val movieId = RandomUtil.id().toString()

        val actual = loadMovieDetailDataSource.loadMovieDetail(
            movieId = movieId,
            language = "",
        )

        assertEquals(
            Result.Success(movieDetailMapperResponse),
            actual
        )
    }

    private fun prepareScenario(
        movieDetailResponse: MovieDetailResponse = MovieDetailResponseFactory.make(),
        movieDetailException: Exception? = null,
        movieDetailMapperModel: MovieDetail = MovieDetailFactory.make(),
    ) {
        if (movieDetailException == null) {
            coEvery {
                movieDetailApi.loadMovieDetails(any(), any())
            } returns movieDetailResponse
        } else {
            coEvery {
                movieDetailApi.loadMovieDetails(any(), any())
            } throws movieDetailException
        }

        every {
            movieDetailResponseToMovieDetailMapper.mapFrom(any())
        } returns movieDetailMapperModel
    }
}