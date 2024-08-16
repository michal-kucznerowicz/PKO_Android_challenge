package com.example.pkoandroidchallenge.data.model

import com.google.gson.annotations.SerializedName

data class SearchMovieList(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
