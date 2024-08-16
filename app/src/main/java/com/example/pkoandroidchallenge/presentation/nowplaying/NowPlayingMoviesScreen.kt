package com.example.pkoandroidchallenge.presentation.nowplaying

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pkoandroidchallenge.R
import com.example.pkoandroidchallenge.domain.model.Movie
import com.example.pkoandroidchallenge.presentation.navigation.Screen
import com.example.pkoandroidchallenge.presentation.nowplaying.components.MovieTile
import com.example.pkoandroidchallenge.presentation.nowplaying.components.Search

@Composable
fun NowPlayingMoviesScreen(
    viewModel: NowPlayingMoviesViewModel,
    navController: NavHostController,
) {
    val state = viewModel.state.collectAsState()
    val moviesPaging = viewModel.moviesPagingFlow.collectAsLazyPagingItems()
    NowPlayingMoviesScreenContent(
        state = state.value,
        dispatch = viewModel::dispatch,
        navController = navController,
        moviesPaging = moviesPaging,
    )
}

@Composable
private fun NowPlayingMoviesScreenContent(
    state: NowPlayingMoviesState,
    dispatch: (NowPlayingMoviesIntent) -> Unit,
    navController: NavHostController,
    moviesPaging: LazyPagingItems<Movie>,
) {
    Column {
        Search(
            state = state,
            dispatch = dispatch,
        )
        if (!state.isShowingSearchResults || state.query.isEmpty()) {
            when (moviesPaging.loadState.refresh) {
                is LoadState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = stringResource(id = R.string.error))
                    }
                }

                is LoadState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 16.dp),
                    ) {
                        items(count = moviesPaging.itemCount) { index ->
                            moviesPaging[index]?.let { movie ->
                                MovieTile(
                                    movie = movie,
                                    isFavorite = state.favoriteMovies.contains(movie.id),
                                    onClick = {
                                        navController.navigate(
                                            route = Screen.MovieDetails(id = movie.id.toString()).route,
                                        )
                                    },
                                    onFavoriteIconClick = {
                                        dispatch(NowPlayingMoviesIntent.OnFavoriteIconClicked(movie.id))
                                    },
                                )
                            }
                        }
                        item {
                            if (moviesPaging.loadState.append is LoadState.Loading) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                            if (moviesPaging.loadState.append is LoadState.NotLoading &&
                                moviesPaging.loadState.refresh is LoadState.NotLoading &&
                                moviesPaging.itemCount == 0
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(text = stringResource(id = R.string.no_movies))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
