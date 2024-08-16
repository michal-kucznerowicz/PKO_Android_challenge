package com.example.pkoandroidchallenge.presentation.navigation

sealed class Screen(val route: String) {
    data object NowPlayingMovies : Screen(route = "now_playing")
    data class MovieDetails(val id: String) : Screen(route = "details/$id")
}
