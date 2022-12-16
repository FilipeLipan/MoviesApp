package com.github.filipelipan.moviesapp.moviedetail.data.repository

import com.github.filipelipan.moviesapp.factories.model.MovieDetailFactory
import com.github.filipelipan.moviesapp.infraestructure.model.Result
import com.github.filipelipan.moviesapp.moviedetail.data.datasource.LoadMovieDetailDataSource
import com.github.filipelipan.moviesapp.moviedetail.domain.error.LoadMovieDetailError
import com.github.filipelipan.moviesapp.moviedetail.domain.model.MovieDetail
import com.github.filipelipan.moviesapp.util.RandomUtil
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MovieDetailRepositoryTest{

    private val loadMovieDetailDataSource: LoadMovieDetailDataSource = mockk()

    private val movieDetailRepository = MovieDetailRepositoryDefault(
        loadMovieDetailDataSource = loadMovieDetailDataSource
    )

    @Test
    fun loadMovie_withSuccess_callDataSourceWithGivenParams() = runTest {
        prepareScenario()

        val movieId = RandomUtil.id().toString()

        movieDetailRepository.loadMovieDetail(
            movieId = movieId,
            language = "",
        )

        coVerify {
            loadMovieDetailDataSource.loadMovieDetail(
                movieId = movieId,
                language = "",
            )
        }
    }

    @Test
    fun loadMovie_withSuccess_returnSuccess() = runTest {
        val result = Result.Success(MovieDetailFactory.make())
        prepareScenario(loadMovieDetailResult = result)

        val actual = movieDetailRepository.loadMovieDetail(
            movieId = "",
            language = "",
        )

        assertEquals(result, actual)
    }

    @Test
    fun loadMovie_withError_returnError() = runTest {
        val result = Result.Error(LoadMovieDetailError.NetworkError)
        prepareScenario(loadMovieDetailResult = result)

        val actual = movieDetailRepository.loadMovieDetail(
            movieId = "",
            language = "",
        )

        assertEquals(result, actual)
    }

    private fun prepareScenario(
        loadMovieDetailResult: Result<MovieDetail, LoadMovieDetailError> =
            Result.Success(MovieDetailFactory.make())
    ) {
        coEvery {
            loadMovieDetailDataSource.loadMovieDetail(any(), any())
        } returns loadMovieDetailResult
    }
}
