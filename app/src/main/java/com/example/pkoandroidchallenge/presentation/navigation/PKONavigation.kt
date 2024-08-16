package com.example.pkoandroidchallenge.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pkoandroidchallenge.presentation.details.MovieDetailsScreen
import com.example.pkoandroidchallenge.presentation.nowplaying.NowPlayingMoviesScreen

@Composable
fun PKONavigation(
    modifier: Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.NowPlayingMovies.route,
    ) {
        composable(route = Screen.NowPlayingMovies.route) {
            NowPlayingMoviesScreen(
                viewModel = hiltViewModel(),
                navController = navController,
            )
        }
        composable(
            route = Screen.MovieDetails("{id}").route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                },
            ),
        ) {
            MovieDetailsScreen(viewModel = hiltViewModel())
        }
    }
}
