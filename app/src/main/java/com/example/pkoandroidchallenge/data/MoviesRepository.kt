package com.example.pkoandroidchallenge.data

import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesApi: MoviesApi,
    private val moviesDataStore: MoviesDataStore
) {

    suspend fun getDetails(id: Int) = moviesApi.getMovieDetails(id)

    suspend fun saveFavouriteMovie(id: Int) = moviesDataStore.saveFavoriteMovie(id)

    fun getFavouriteMovies() = moviesDataStore.getFavoriteMovies()

    suspend fun searchMovie(query: String) = moviesApi.searchMovie(query)
}
