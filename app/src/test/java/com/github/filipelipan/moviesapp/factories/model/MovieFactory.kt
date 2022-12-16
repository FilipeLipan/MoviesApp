package com.github.filipelipan.moviesapp.factories.model

import com.github.filipelipan.moviesapp.infraestructure.model.ImageUrl
import com.github.filipelipan.moviesapp.movielist.domain.model.Movie
import com.github.filipelipan.moviesapp.util.RandomUtil

object MovieFactory {
    fun make(
        id: Int = RandomUtil.id(),
        title: String = RandomUtil.fullName(),
        poster_path: ImageUrl.LowDefinitionImage = ImageUrl.LowDefinitionImage(RandomUtil.name()),
        rating: String = RandomUtil.int().toString(),
        overview: String = RandomUtil.name(),
    ) = Movie(
        id = id,
        name = title,
        imageUrl = poster_path,
        rating = rating,
    )
}
