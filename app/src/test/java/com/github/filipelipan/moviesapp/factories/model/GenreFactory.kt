package com.github.filipelipan.moviesapp.factories.model

import com.github.filipelipan.moviesapp.moviedetail.domain.model.Genre
import com.github.filipelipan.moviesapp.util.RandomUtil

object GenreFactory {
    fun make(
        id: Int = RandomUtil.int(),
        name: String = RandomUtil.name(),
    ) = Genre(
        id = RandomUtil.id(),
        name = RandomUtil.name()
    )
}
