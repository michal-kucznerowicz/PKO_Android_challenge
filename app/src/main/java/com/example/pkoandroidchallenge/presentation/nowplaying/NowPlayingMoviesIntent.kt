package com.example.pkoandroidchallenge.presentation.nowplaying

sealed interface NowPlayingMoviesIntent {
    data class OnFavoriteIconClicked(val id: Int) : NowPlayingMoviesIntent
    data class OnQueryChanged(val query: String) : NowPlayingMoviesIntent
    data class OnResultsVisibilityChanged(val isVisible: Boolean) : NowPlayingMoviesIntent
}
