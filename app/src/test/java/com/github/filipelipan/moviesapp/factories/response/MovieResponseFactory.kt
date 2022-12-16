package com.github.filipelipan.moviesapp.factories.response

import com.github.filipelipan.moviesapp.movielist.data.response.MovieResponse
import com.github.filipelipan.moviesapp.util.RandomUtil

object MovieResponseFactory {
    fun make(
        id: Int = RandomUtil.id(),
        title: String = RandomUtil.fullName(),
        poster_path: String = RandomUtil.name(),
        vote_average: String = RandomUtil.int().toString(),
        overview: String = RandomUtil.name(),
    ) = MovieResponse(
        id = id,
        title = title,
        poster_path = poster_path,
        vote_average = vote_average,
        overview = overview,
    )
}
