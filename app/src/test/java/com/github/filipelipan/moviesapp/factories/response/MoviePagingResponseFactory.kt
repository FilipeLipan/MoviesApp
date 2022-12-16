package com.github.filipelipan.moviesapp.factories.response

import com.github.filipelipan.moviesapp.movielist.data.response.MoviePagingResponse
import com.github.filipelipan.moviesapp.movielist.data.response.MovieResponse
import com.github.filipelipan.moviesapp.util.RandomUtil

object MoviePagingResponseFactory {
    fun make(
        page: Int = RandomUtil.id(),
        total_pages: Int = RandomUtil.int(),
        movies: List<MovieResponse> = RandomUtil.listOf { MovieResponseFactory.make() },
    ) = MoviePagingResponse(
        page = page,
        total_pages = total_pages,
        movies = movies,
    )
}
