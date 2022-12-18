package com.github.filipelipan.moviesapp.navigation

import com.github.filipelipan.moviesapp.navigation.AppDestinations.MOVIE_DETAIL_SCREEN
import com.github.filipelipan.moviesapp.navigation.AppDestinations.MOVIE_LIST_SCREEN
import com.github.filipelipan.moviesapp.navigation.AppDestinationsArgs.MOVIE_ID_ARG

object AppDestinations {
    const val MOVIE_LIST_SCREEN = "movielist"
    const val MOVIE_DETAIL_SCREEN = "moviedetail"
}

object AppDestinationsArgs {
    const val MOVIE_ID_ARG = "movieId"
}

object AppRoutes {
    const val MOVIE_LIST_ROUTE = MOVIE_LIST_SCREEN
    const val MOVIE_DETAIL_ROUTE = "$MOVIE_DETAIL_SCREEN/{$MOVIE_ID_ARG}"
}
