package com.example.pkoandroidchallenge.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.pkoandroidchallenge.data.MoviesApi
import com.example.pkoandroidchallenge.data.NowPlayingMoviesPagingSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class GetNowPlayingMoviesUseCase @Inject constructor(
    private val moviesApi: MoviesApi,
) {
    private val pagingScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(query: Flow<String>) =
        query
            .distinctUntilChanged()
            .flatMapLatest {
                Pager(
                    config = PagingConfig(pageSize = 20),
                    pagingSourceFactory = {
                        NowPlayingMoviesPagingSource(moviesApi, it)
                    },
                ).flow.cachedIn(pagingScope)
            }

    fun clear() {
        pagingScope.cancel()
    }
}
