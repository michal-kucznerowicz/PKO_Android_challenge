package com.example.pkoandroidchallenge.domain

import com.example.pkoandroidchallenge.data.MoviesRepository
import javax.inject.Inject

class SaveFavoriteMovieUseCase @Inject constructor(
    private val repository: MoviesRepository,
) {
    suspend operator fun invoke(id: Int) {
        repository.saveFavouriteMovie(id)
    }
}
