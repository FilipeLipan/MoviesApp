package com.github.filipelipan.moviesapp.movielist.domain.model

import com.github.filipelipan.moviesapp.infraestructure.model.ImageUrl

data class Movie(
    val id: Int,
    val name: String,
    val imageUrl: ImageUrl.LowDefinitionImage,
    val rating: String,
)
