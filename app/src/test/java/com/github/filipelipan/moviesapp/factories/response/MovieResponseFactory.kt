package com.github.filipelipan.moviesapp.factories.response

import com.github.filipelipan.moviesapp.movielist.data.response.MovieResponse
import com.github.filipelipan.moviesapp.util.RandomUtil

object MovieResponseFactory {
    fun make(
        id: Int = RandomUtil.id(),
        title: String = RandomUtil.fullName(),
        posterPath: String = RandomUtil.name(),
        voteAverage: String = RandomUtil.int().toString(),
        overview: String = RandomUtil.name(),
    ) = MovieResponse(
        id = id,
        title = title,
        posterPath = posterPath,
        voteAverage = voteAverage,
        overview = overview,
    )
}
