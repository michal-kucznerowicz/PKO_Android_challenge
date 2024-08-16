package com.example.pkoandroidchallenge.data.mapper

import com.example.pkoandroidchallenge.data.model.MovieDto
import com.example.pkoandroidchallenge.domain.model.Movie

fun MovieDto.toDomain() = Movie(
    id = id,
    backdropPath = backdropPath,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    voteAverage = voteAverage,
    voteCount = voteCount,
)
