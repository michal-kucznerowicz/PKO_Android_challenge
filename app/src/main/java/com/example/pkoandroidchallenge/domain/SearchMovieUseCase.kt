package com.example.pkoandroidchallenge.domain

import com.example.pkoandroidchallenge.data.MoviesRepository
import com.example.pkoandroidchallenge.data.mapper.toDomain
import com.example.pkoandroidchallenge.domain.model.Movie
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val repository: MoviesRepository,
) {
    suspend operator fun invoke(query: String): List<Movie> = runCatching {
        val response = repository.searchMovie(query)
        return if (response.isSuccessful) {
            response.body()?.results?.map { it.toDomain() } ?: emptyList()
        } else {
            emptyList()
        }
    }.getOrElse {
        emptyList()
    }
}
