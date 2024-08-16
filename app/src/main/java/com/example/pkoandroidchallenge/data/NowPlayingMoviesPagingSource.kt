package com.example.pkoandroidchallenge.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pkoandroidchallenge.data.mapper.toDomain
import com.example.pkoandroidchallenge.data.model.MovieList
import com.example.pkoandroidchallenge.domain.model.Movie

class NowPlayingMoviesPagingSource(
    private val moviesApi: MoviesApi,
    private val query: String,
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? =
        state
            .anchorPosition
            ?.let {
                state.closestPageToPosition(it)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
            }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> =
        try {
            val page = params.key ?: 1
            val response = moviesApi.getNowPlayingMovies(page)
            if (response.isSuccessful) {
                response
                    .body()
                    ?.let { movieList ->
                        LoadResult.Page(
                            data = getMoviesData(movieList),
                            prevKey = if (page > 1) page - 1 else null,
                            nextKey = if (movieList.movies.isNotEmpty()) page + 1 else null,
                        )
                    } ?: LoadResult.Error(Exception("Empty response body"))
            } else {
                LoadResult.Error(Exception("Response not successful"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    private fun getMoviesData(movieList: MovieList) =
        movieList
            .movies
            .map { it.toDomain() }
            .filter {
                it.title?.startsWith(
                    prefix = query,
                    ignoreCase = true,
                ) == true
            }
}
