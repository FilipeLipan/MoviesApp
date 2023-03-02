package com.github.filipelipan.moviesapp.moviedetail.data.mapper

import com.github.filipelipan.moviesapp.moviedetail.data.response.MovieDetailResponse
import com.github.filipelipan.moviesapp.moviedetail.domain.model.MovieDetail
import java.util.*

interface MovieDetailResponseToMovieDetailMapper {
    fun mapFrom(movieDetailResponse: MovieDetailResponse, locale: Locale = Locale.ENGLISH): MovieDetail
}
