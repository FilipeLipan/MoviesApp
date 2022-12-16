package com.github.filipelipan.moviesapp.navigation

import androidx.navigation.NavHostController
import com.github.filipelipan.moviesapp.navigation.MovieDestinations.MOVIE_DETAIL_SCREEN
import com.github.filipelipan.moviesapp.navigation.MovieDestinations.MOVIE_LIST_SCREEN

class MovieNavigationActions(private val navController: NavHostController) {

    fun navigateToMovieList() {
        navController.navigate(MOVIE_LIST_SCREEN)
    }

    fun navigateToMovieDetail(movieId: Int) {
        navController.navigate("$MOVIE_DETAIL_SCREEN/$movieId")
    }
}
