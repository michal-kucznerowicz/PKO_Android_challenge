package com.example.pkoandroidchallenge.presentation.details

sealed interface MovieDetailsIntent {
    data object OnFavoriteIconClicked : MovieDetailsIntent
}
