package com.github.filipelipan.moviesapp.factories.response

import com.github.filipelipan.moviesapp.moviedetail.data.response.GenreResponse
import com.github.filipelipan.moviesapp.moviedetail.data.response.MovieDetailResponse
import com.github.filipelipan.moviesapp.util.RandomUtil

object MovieDetailResponseFactory {
    fun make(
        id: Int = RandomUtil.id(),
        title: String = RandomUtil.fullName(),
        posterPath: String = RandomUtil.name(),
        voteAverage: Double = RandomUtil.double(),
        voteCount: String = RandomUtil.int().toString(),
        overview: String = RandomUtil.name(),
        genres: List<GenreResponse> = RandomUtil.listOf { GenreResponseFactory.make() },
        releaseDate: String = "2022-10-19"
    ) = MovieDetailResponse(
        id = id,
        title = title,
        posterPath = posterPath,
        voteAverage = voteAverage,
        voteCount = voteCount,
        overview = overview,
        genres = genres,
        releaseDate = releaseDate,
    )
}
