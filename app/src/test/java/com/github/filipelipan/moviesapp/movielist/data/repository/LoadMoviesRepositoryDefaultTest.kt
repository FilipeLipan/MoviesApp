package com.github.filipelipan.moviesapp.movielist.data.repository

import com.github.filipelipan.moviesapp.factories.model.PagingFactory
import com.github.filipelipan.moviesapp.infraestructure.model.Result
import com.github.filipelipan.moviesapp.movielist.data.datasource.LoadMoviesDataSource
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

class LoadMoviesRepositoryDefaultTest {

    private val loadMoviesDataSource: LoadMoviesDataSource = mockk()
    private val loadMoviesRepository = LoadMoviesRepositoryDefault(
        loadMoviesDataSource = loadMoviesDataSource
    )

    @Test
    fun loadMovie_withSuccess_callDataSourceWithGivenParams() = runTest {
        prepareScenario()
        val language = RandomUtil.name()
        val page = RandomUtil.int().toString()
        val loadType = RandomUtil.name()

        loadMoviesRepository.loadMovies(
            language = language,
            page = page,
            loadType = loadType,
        )

        coVerify {
            loadMoviesDataSource.loadMovies(
                language = language,
                page = page,
                loadType = loadType,
            )
        }
    }

    @Test
    fun loadMovie_withSuccess_returnSuccess() = runTest {
        val result = Result.Success(PagingFactory.make())
        prepareScenario(loadMoviesResult = result)

        val actual = loadMoviesRepository.loadMovies("", "", "")

        assertEquals(result, actual)
    }

    @Test
    fun loadMovie_withError_returnError() = runTest {
        val result = Result.Error(LoadMoviesError.NetworkError)
        prepareScenario(loadMoviesResult = result)

        val actual = loadMoviesRepository.loadMovies("", "", "")

        assertEquals(result, actual)
    }

    private fun prepareScenario(
        loadMoviesResult: Result<Pagination<Movie>, LoadMoviesError> =
            Result.Success(PagingFactory.make())
    ) {
        coEvery {
            loadMoviesDataSource.loadMovies(any(), any(), any())
        } returns loadMoviesResult
    }
}
