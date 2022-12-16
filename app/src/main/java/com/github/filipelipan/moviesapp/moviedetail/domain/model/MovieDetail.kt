package com.github.filipelipan.moviesapp.moviedetail.domain.model

import com.github.filipelipan.moviesapp.infraestructure.model.ImageUrl

data class MovieDetail(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: ImageUrl.HighDefinitionImage,
    val genres: List<Genre>,
    val overview: String,
    val voteCount: String,
)
