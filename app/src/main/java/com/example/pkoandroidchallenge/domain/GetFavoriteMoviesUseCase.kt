package com.example.pkoandroidchallenge.domain

import com.example.pkoandroidchallenge.data.MoviesRepository
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository,
) {
    operator fun invoke() = repository.getFavouriteMovies()
}
