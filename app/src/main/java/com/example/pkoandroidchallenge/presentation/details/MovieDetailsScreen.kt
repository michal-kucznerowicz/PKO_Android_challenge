package com.example.pkoandroidchallenge.presentation.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.pkoandroidchallenge.R
import com.example.pkoandroidchallenge.util.Constants.IMAGE_URL

@Composable
fun MovieDetailsScreen(viewModel: MovieDetailsViewModel) {
    val state = viewModel.state.collectAsState()
    MovieDetailsScreenContent(
        state = state.value,
        dispatch = viewModel::dispatch,
    )
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun MovieDetailsScreenContent(
    state: MovieDetailsState,
    dispatch: (MovieDetailsIntent) -> Unit,
) {
    state.movie?.let { movie ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            Box {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.7777778f),
                    painter = rememberImagePainter(
                        data = IMAGE_URL + movie.backdropPath,
                    ),
                    contentDescription = stringResource(id = R.string.movie_backdrop_image),
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black))),
                )
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomStart),
                ) {
                    movie.title?.let {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White,
                        )
                    }
                    movie.releaseDate?.let {
                        Text(
                            text = it,
                            color = Color.White,
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    movie.voteAverage?.let {
                        val annotatedString = buildAnnotatedString {
                            append(
                                AnnotatedString(
                                    text = (Math.round(it * 10.0) / 10.0).toString(),
                                    spanStyle = SpanStyle(fontWeight = FontWeight.Bold),
                                )
                            )
                            append("/10")
                        }
                        Text(
                            text = annotatedString,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                    movie.voteCount?.let {
                        Text(
                            text = stringResource(id = R.string.votes, it),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
                Icon(
                    modifier = Modifier
                        .size(32.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                        ) {
                            dispatch(MovieDetailsIntent.OnFavoriteIconClicked)
                        },
                    imageVector = if (state.isFavorite) {
                        Icons.Default.Star
                    } else {
                        Icons.Default.StarOutline
                    },
                    contentDescription = stringResource(id = R.string.toggle_favorite),
                )
            }
            movie.overview?.let {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = it,
                    textAlign = TextAlign.Justify,
                )
            }
        }
    } ?: Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}
