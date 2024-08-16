package com.example.pkoandroidchallenge.presentation.nowplaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pkoandroidchallenge.domain.GetFavoriteMoviesUseCase
import com.example.pkoandroidchallenge.domain.GetNowPlayingMoviesUseCase
import com.example.pkoandroidchallenge.domain.SaveFavoriteMovieUseCase
import com.example.pkoandroidchallenge.domain.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NowPlayingMoviesViewModel @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val saveFavoriteMovieUseCase: SaveFavoriteMovieUseCase,
    private val searchMovieUseCase: SearchMovieUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(NowPlayingMoviesState())
    val state = _state.asStateFlow()
    val moviesPagingFlow = getNowPlayingMoviesUseCase(state.map { it.query })

    init {
        viewModelScope.launch {
            getFavoriteMoviesUseCase().collect { favoriteMovies ->
                _state.update {
                    it.copy(favoriteMovies = favoriteMovies)
                }
            }
        }
    }

    fun dispatch(intent: NowPlayingMoviesIntent) {
        when (intent) {
            is NowPlayingMoviesIntent.OnFavoriteIconClicked -> handleOnFavoriteIconClicked(intent.id)
            is NowPlayingMoviesIntent.OnQueryChanged -> handleOnQueryChanged(intent.query)
            is NowPlayingMoviesIntent.OnResultsVisibilityChanged -> handleOnResultsVisibilityChanged(
                intent.isVisible
            )
        }
    }

    private fun handleOnFavoriteIconClicked(id: Int) {
        viewModelScope.launch {
            saveFavoriteMovieUseCase(id)
        }
    }

    private fun handleOnQueryChanged(query: String) {
        _state.update {
            it.copy(query = query)
        }
        viewModelScope.launch {
            _state.update {
                it.copy(searchResult = searchMovieUseCase(query))
            }
        }
    }

    private fun handleOnResultsVisibilityChanged(isVisible: Boolean) {
        _state.update {
            it.copy(isShowingSearchResults = isVisible)
        }
    }

    override fun onCleared() {
        super.onCleared()
        getNowPlayingMoviesUseCase.clear()
    }
}
