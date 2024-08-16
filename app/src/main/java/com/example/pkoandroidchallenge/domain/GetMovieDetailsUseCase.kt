package com.example.pkoandroidchallenge.domain

import com.example.pkoandroidchallenge.data.MoviesRepository
import com.example.pkoandroidchallenge.data.mapper.toDomain
import com.example.pkoandroidchallenge.domain.model.Movie
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MoviesRepository,
) {
    suspend operator fun invoke(id: Int): Movie? = runCatching {
        val details = repository.getDetails(id)
        return if (details.isSuccessful) {
            details.body()?.toDomain()
        } else {
            null
        }
    }.getOrNull()
}
