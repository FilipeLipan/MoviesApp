package com.github.filipelipan.moviesapp.moviedetail.data.mapper

import com.github.filipelipan.moviesapp.moviedetail.data.response.MovieDetailResponse
import com.github.filipelipan.moviesapp.moviedetail.domain.model.MovieDetail

interface MovieDetailResponseToMovieDetailMapper {
    fun mapFrom(movieDetailResponse: MovieDetailResponse): MovieDetail
}
