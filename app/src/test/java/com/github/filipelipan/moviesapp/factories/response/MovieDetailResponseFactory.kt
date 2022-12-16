package com.github.filipelipan.moviesapp.factories.response

import androidx.compose.foundation.interaction.PressInteraction
import com.github.filipelipan.moviesapp.moviedetail.data.response.GenreResponse
import com.github.filipelipan.moviesapp.moviedetail.data.response.MovieDetailResponse
import com.github.filipelipan.moviesapp.util.RandomUtil

object MovieDetailResponseFactory {
    fun make(
        id: Int = RandomUtil.id(),
        title: String = RandomUtil.fullName(),
        poster_path: String = RandomUtil.name(),
        vote_average: Double = RandomUtil.double(),
        vote_count: String = RandomUtil.int().toString(),
        overview: String = RandomUtil.name(),
        genres: List<GenreResponse> = RandomUtil.listOf { GenreResponseFactory.make() },
        release_date: String = "2022-10-19"
    ) = MovieDetailResponse(
        id = id,
        title = title,
        poster_path = poster_path,
        vote_average = vote_average,
        vote_count = vote_count,
        overview = overview,
        genres = genres,
        release_date = release_date,
    )
}
