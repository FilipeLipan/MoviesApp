package com.github.filipelipan.moviesapp.navigation

import com.github.filipelipan.moviesapp.navigation.MovieDestinations.MOVIE_DETAIL_SCREEN
import com.github.filipelipan.moviesapp.navigation.MovieDestinations.MOVIE_LIST_SCREEN
import com.github.filipelipan.moviesapp.navigation.MovieDestinationsArgs.MOVIE_ID_ARG

object MovieDestinations {
    const val MOVIE_LIST_SCREEN = "movielist"
    const val MOVIE_DETAIL_SCREEN = "moviedetail"
}

object MovieDestinationsArgs {
    const val MOVIE_ID_ARG = "movieId"
}

object TodoDestinations {
    const val MOVIE_LIST_ROUTE = MOVIE_LIST_SCREEN
    const val MOVIE_DETAIL_ROUTE = "$MOVIE_DETAIL_SCREEN/{$MOVIE_ID_ARG}"
}
