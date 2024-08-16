package com.example.pkoandroidchallenge.presentation.nowplaying.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.pkoandroidchallenge.R
import com.example.pkoandroidchallenge.domain.model.Movie
import com.example.pkoandroidchallenge.util.Constants.IMAGE_URL

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MovieTile(
    modifier: Modifier = Modifier,
    movie: Movie,
    isFavorite: Boolean,
    onClick: () -> Unit,
    onFavoriteIconClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        onClick = onClick,
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                modifier = Modifier
                    .size(100.dp, 150.dp)
                    .clip(RoundedCornerShape(8.dp)),
                painter = rememberImagePainter(
                    data = IMAGE_URL + movie.posterPath,
                    builder = {
                        placeholder(R.drawable.movie_poster_placeholder)
                    }
                ),
                contentDescription = stringResource(id = R.string.movie_poster_image),
            )
            Column(
                modifier = Modifier.padding(start = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    movie.title?.let {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = it,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                    Icon(
                        modifier = Modifier.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                        ) {
                            onFavoriteIconClick()
                        },
                        imageVector = if (isFavorite) {
                            Icons.Default.Star
                        } else {
                            Icons.Default.StarOutline
                        },
                        contentDescription = stringResource(id = R.string.toggle_favorite),
                    )
                }
                movie.releaseDate?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
                movie.overview?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Justify,
                    )
                }
            }
        }
    }
}
