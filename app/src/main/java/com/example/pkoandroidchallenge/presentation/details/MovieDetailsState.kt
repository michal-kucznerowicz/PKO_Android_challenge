package com.example.pkoandroidchallenge.presentation.details

import com.example.pkoandroidchallenge.domain.model.Movie

data class MovieDetailsState(
    val movie: Movie? = null,
    val isFavorite: Boolean = false,
)
