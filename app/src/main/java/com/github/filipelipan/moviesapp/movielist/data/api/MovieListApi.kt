package com.github.filipelipan.moviesapp.movielist.data.api

import com.github.filipelipan.moviesapp.movielist.data.response.MoviePagingResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieListApi {
    @GET("/3/movie/{path}")
    suspend fun loadMovies(
        @Path("path") loadType: String,
        @Query("language") language: String,
        @Query("page") page: String,
    ): MoviePagingResponse
}
