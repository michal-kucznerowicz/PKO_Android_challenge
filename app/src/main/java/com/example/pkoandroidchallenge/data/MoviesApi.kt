package com.example.pkoandroidchallenge.data

import com.example.pkoandroidchallenge.data.model.MovieDto
import com.example.pkoandroidchallenge.data.model.MovieList
import com.example.pkoandroidchallenge.data.model.SearchMovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("/3/movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("page") page: Int): Response<MovieList>

    @GET("/3/movie/{id}")
    suspend fun getMovieDetails(@Path("id") id: Int): Response<MovieDto>

    @GET("/3/search/movie")
    suspend fun searchMovie(@Query("query") query: String): Response<SearchMovieList>
}
