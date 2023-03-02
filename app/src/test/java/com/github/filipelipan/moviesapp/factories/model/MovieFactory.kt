package com.github.filipelipan.moviesapp.factories.model

import com.github.filipelipan.moviesapp.infraestructure.model.ImageUrl
import com.github.filipelipan.moviesapp.movielist.domain.model.Movie
import com.github.filipelipan.moviesapp.util.RandomUtil

object MovieFactory {
    fun make(
        id: Int = RandomUtil.id(),
        title: String = RandomUtil.fullName(),
        posterPath: ImageUrl.LowDefinitionImage = ImageUrl.LowDefinitionImage(RandomUtil.name()),
        rating: String = RandomUtil.int().toString(),
    ) = Movie(
        id = id,
        name = title,
        imageUrl = posterPath,
        rating = rating,
    )
}
