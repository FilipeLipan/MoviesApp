package com.github.filipelipan.moviesapp.movielist.domain.usecase

import com.github.filipelipan.moviesapp.factories.model.PagingFactory
import com.github.filipelipan.moviesapp.infraestructure.model.Result
import com.github.filipelipan.moviesapp.movielist.data.repository.LoadMoviesRepository
import com.github.filipelipan.moviesapp.movielist.domain.error.LoadMoviesError
import com.github.filipelipan.moviesapp.movielist.domain.model.Movie
import com.github.filipelipan.moviesapp.movielist.domain.model.Pagination
import com.github.filipelipan.moviesapp.util.RandomUtil
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class LoadMoviesUseCaseDefaultTest {
    private val loadMoviesRepository: LoadMoviesRepository = mockk()
    private val loadMoviesUseCase = LoadMoviesUseCaseDefault(
        loadMoviesRepository = loadMoviesRepository
    )

    @Test
    fun loadMovie_withSuccess_callDataSourceWithGivenParams() = runTest {
        prepareScenario()
        val language = RandomUtil.name()
        val page = RandomUtil.int().toString()
        val genreKey = RandomUtil.name()

        loadMoviesUseCase(
            language = language,
            page = page,
            genreKey = genreKey,
        )

        coVerify {
            loadMoviesRepository.loadMovies(
                language = language,
                page = page,
                loadType = genreKey,
            )
        }
    }

    @Test
    fun loadMovie_withSuccess_returnSuccess() = runTest {
        val result = Result.Success(PagingFactory.make())
        prepareScenario(loadMoviesResult = result)

        val actual = loadMoviesUseCase("", "", "")

        assertEquals(result, actual)
    }

    @Test
    fun loadMovie_withError_returnError() = runTest {
        val result = Result.Error(LoadMoviesError.NetworkError)
        prepareScenario(loadMoviesResult = result)

        val actual = loadMoviesUseCase("", "", "")

        assertEquals(result, actual)
    }

    private fun prepareScenario(
        loadMoviesResult: Result<Pagination<Movie>, LoadMoviesError> =
            Result.Success(PagingFactory.make())
    ) {
        coEvery {
            loadMoviesRepository.loadMovies(any(), any(), any())
        } returns loadMoviesResult
    }
}
