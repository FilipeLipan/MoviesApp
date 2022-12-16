package com.github.filipelipan.moviesapp.moviedetail.data.api

import com.github.filipelipan.moviesapp.moviedetail.data.response.MovieDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailApi {
    @GET("/3/movie/{movie_id}")
    suspend fun loadMovieDetails(
        @Path("movie_id") movieId: String,
        @Query("language") language: String,
    ): MovieDetailResponse
}
