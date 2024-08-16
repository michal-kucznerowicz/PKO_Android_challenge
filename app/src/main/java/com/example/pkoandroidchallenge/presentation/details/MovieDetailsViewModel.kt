package com.example.pkoandroidchallenge.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pkoandroidchallenge.domain.GetMovieDetailsUseCase
import com.example.pkoandroidchallenge.domain.GetFavoriteMoviesUseCase
import com.example.pkoandroidchallenge.domain.SaveFavoriteMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val saveFavoriteMovieUseCase: SaveFavoriteMovieUseCase,
) : ViewModel() {

    private val id: Int = checkNotNull(savedStateHandle["id"])
    private val _state = MutableStateFlow(MovieDetailsState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(movie = getMovieDetailsUseCase(id))
            }
            getFavoriteMoviesUseCase().collect { favoriteMovies ->
                _state.update {
                    it.copy(isFavorite = favoriteMovies.contains(id))
                }
            }
        }
    }

    fun dispatch(intent: MovieDetailsIntent) {
        when (intent) {
            is MovieDetailsIntent.OnFavoriteIconClicked -> handleOnFavoriteIconClicked()
        }
    }

    private fun handleOnFavoriteIconClicked() {
        viewModelScope.launch {
            saveFavoriteMovieUseCase(id)
        }
    }
}
