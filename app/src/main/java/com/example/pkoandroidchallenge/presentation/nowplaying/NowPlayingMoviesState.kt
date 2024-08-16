package com.example.pkoandroidchallenge.presentation.nowplaying

import com.example.pkoandroidchallenge.domain.model.Movie

data class NowPlayingMoviesState(
    val favoriteMovies: Set<Int> = emptySet(),
    val searchResult: List<Movie> = emptyList(),
    val query: String = "",
    val isShowingSearchResults: Boolean = false,
)
