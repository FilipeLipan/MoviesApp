package com.github.filipelipan.moviesapp.moviedetail.domain.usecase

import com.github.filipelipan.moviesapp.factories.model.MovieDetailFactory
import com.github.filipelipan.moviesapp.infraestructure.model.Result
import com.github.filipelipan.moviesapp.moviedetail.data.repository.MovieDetailRepository
import com.github.filipelipan.moviesapp.moviedetail.domain.error.LoadMovieDetailError
import com.github.filipelipan.moviesapp.moviedetail.domain.model.MovieDetail
import com.github.filipelipan.moviesapp.util.RandomUtil
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class LoadMovieDetailCaseTest {
    private val movieDetailRepository: MovieDetailRepository = mockk()
    private val loadMovieDetailUseCase = LoadMovieDetailCaseDefault(
        movieDetailRepository = movieDetailRepository
    )

    @Test
    fun loadMovie_withSuccess_callDataSourceWithGivenParams() = runTest {
        prepareScenario()

        val movieId = RandomUtil.id().toString()

        loadMovieDetailUseCase(movieId)

        coVerify {
            movieDetailRepository.loadMovieDetail(
                movieId = movieId,
                language = "en-US",
            )
        }
    }

    @Test
    fun loadMovie_withSuccess_returnSuccess() = runTest {
        val result = Result.Success(MovieDetailFactory.make())
        prepareScenario(loadMovieDetailResult = result)

        val actual = loadMovieDetailUseCase("")

        assertEquals(result, actual)
    }

    @Test
    fun loadMovie_withError_returnError() = runTest {
        val result = Result.Error(LoadMovieDetailError.NetworkError)
        prepareScenario(loadMovieDetailResult = result)

        val actual = loadMovieDetailUseCase("")

        assertEquals(result, actual)
    }

    private fun prepareScenario(
        loadMovieDetailResult: Result<MovieDetail, LoadMovieDetailError> =
            Result.Success(MovieDetailFactory.make())
    ) {
        coEvery {
            movieDetailRepository.loadMovieDetail(any(), any())
        } returns loadMovieDetailResult
    }
}
