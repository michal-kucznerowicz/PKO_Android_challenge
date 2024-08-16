package com.example.pkoandroidchallenge.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore("movies")

class MoviesDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val favoriteMoviesKey = stringPreferencesKey("favorite_movies")

    suspend fun saveFavoriteMovie(id: Int) {
        val favoriteMovies = getFavoriteMovies().first()
        val ids = if (favoriteMovies.contains(id)) {
            favoriteMovies.minus(id)
        } else {
            favoriteMovies.plus(id)
        }
        val idsString = ids.joinToString(separator = ",")
        context
            .dataStore
            .edit { preferences ->
                preferences[favoriteMoviesKey] = idsString
            }
    }

    fun getFavoriteMovies(): Flow<Set<Int>> =
        context
            .dataStore
            .data
            .map { preferences ->
                val idsString = preferences[favoriteMoviesKey] ?: ""
                if (idsString.isEmpty()) {
                    emptySet()
                } else {
                    idsString
                        .split(",")
                        .map { it.toInt() }
                        .toSet()
                }
            }
}
