package com.github.filipelipan.moviesapp.factories.response

import com.github.filipelipan.moviesapp.moviedetail.data.response.GenreResponse
import com.github.filipelipan.moviesapp.util.RandomUtil

object GenreResponseFactory {
    fun make(
        id: Int = RandomUtil.int(),
        name: String = RandomUtil.name(),
    ) = GenreResponse(
        id = id,
        name = name,
    )
}
