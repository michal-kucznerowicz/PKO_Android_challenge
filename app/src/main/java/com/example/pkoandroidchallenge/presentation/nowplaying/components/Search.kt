package com.example.pkoandroidchallenge.presentation.nowplaying.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.pkoandroidchallenge.R
import com.example.pkoandroidchallenge.presentation.nowplaying.NowPlayingMoviesIntent
import com.example.pkoandroidchallenge.presentation.nowplaying.NowPlayingMoviesState

@Composable
fun Search(
    modifier: Modifier = Modifier,
    state: NowPlayingMoviesState,
    dispatch: (NowPlayingMoviesIntent) -> Unit,
) {
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    dispatch(NowPlayingMoviesIntent.OnResultsVisibilityChanged(false))
                }
            )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned {
                        textFieldSize = it.size.toSize()
                    },
                value = state.query,
                onValueChange = {
                    dispatch(NowPlayingMoviesIntent.OnQueryChanged(it))
                    dispatch(NowPlayingMoviesIntent.OnResultsVisibilityChanged(true))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search,
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus()
                        dispatch(NowPlayingMoviesIntent.OnQueryChanged(state.query))
                        dispatch(NowPlayingMoviesIntent.OnResultsVisibilityChanged(false))
                        keyboardController?.hide()
                    }
                ),
                singleLine = true,
                leadingIcon = {
                    if (state.query.isEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.search),
                        )
                    } else {
                        Icon(
                            modifier = Modifier.clickable {
                                dispatch(NowPlayingMoviesIntent.OnQueryChanged(""))
                            },
                            imageVector = Icons.Default.Clear,
                            contentDescription = stringResource(id = R.string.clear),
                        )
                    }
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.search))
                }
            )
            AnimatedVisibility(visible = state.isShowingSearchResults) {
                Box(modifier = Modifier.width(textFieldSize.width.dp)) {
                    LazyColumn {
                        if (state.query.isNotEmpty()) {
                            items(state.searchResult) { movie ->
                                movie.title?.let { title ->
                                    QueryItem(
                                        title = title,
                                        onSelect = {
                                            focusManager.clearFocus()
                                            dispatch(NowPlayingMoviesIntent.OnQueryChanged(it))
                                            dispatch(
                                                NowPlayingMoviesIntent.OnResultsVisibilityChanged(
                                                    false
                                                )
                                            )
                                        },
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
