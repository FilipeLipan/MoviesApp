package com.github.filipelipan.moviesapp.factories.model

import com.github.filipelipan.moviesapp.movielist.domain.model.Movie
import com.github.filipelipan.moviesapp.movielist.domain.model.Pagination
import com.github.filipelipan.moviesapp.util.RandomUtil

object PagingFactory {
    fun make(
        page: Int = RandomUtil.id(),
        totalPages: Int = RandomUtil.int(),
        movies: List<Movie> = RandomUtil.listOf { MovieFactory.make() },
    ) = Pagination(
        page = page,
        totalPages = totalPages,
        movies = movies,
    )
}
