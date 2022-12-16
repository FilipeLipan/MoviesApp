package com.github.filipelipan.moviesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.filipelipan.moviesapp.moviedetail.MovieDetailScreen
import com.github.filipelipan.moviesapp.movielist.MovieListScreen
import com.github.filipelipan.moviesapp.navigation.TodoDestinations.MOVIE_DETAIL_ROUTE
import com.github.filipelipan.moviesapp.navigation.TodoDestinations.MOVIE_LIST_ROUTE
import kotlinx.coroutines.CoroutineScope

@Composable
fun MoviesNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    startDestination: String = MovieDestinations.MOVIE_LIST_SCREEN,
    navActions: MovieNavigationActions = remember(navController) {
        MovieNavigationActions(navController)
    }
) {

    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(MOVIE_LIST_ROUTE) {
            MovieListScreen(
                onBack = { navController.popBackStack() },
                onMovieItemClick = { movieId -> navActions.navigateToMovieDetail(movieId) },
            )
        }

        composable(MOVIE_DETAIL_ROUTE) {
            MovieDetailScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
