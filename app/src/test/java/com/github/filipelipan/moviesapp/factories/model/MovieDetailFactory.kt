package com.github.filipelipan.moviesapp.factories.model

import com.github.filipelipan.moviesapp.infraestructure.model.ImageUrl
import com.github.filipelipan.moviesapp.moviedetail.domain.model.Genre
import com.github.filipelipan.moviesapp.moviedetail.domain.model.MovieDetail
import com.github.filipelipan.moviesapp.util.RandomUtil

object MovieDetailFactory {
    fun make(
        id: Int = RandomUtil.id(),
        name: String = RandomUtil.fullName(),
        imageUrl: ImageUrl.HighDefinitionImage = ImageUrl.HighDefinitionImage(RandomUtil.name()),
        voteCount: String = RandomUtil.int().toString(),
        overview: String = RandomUtil.name(),
        description: String = RandomUtil.name(),
        genres: List<Genre> = RandomUtil.listOf { GenreFactory.make() },
    ) = MovieDetail(
        id = id,
        name = name,
        imageUrl = imageUrl,
        voteCount = voteCount,
        overview = overview,
        genres = genres,
        description = description,
    )
}
