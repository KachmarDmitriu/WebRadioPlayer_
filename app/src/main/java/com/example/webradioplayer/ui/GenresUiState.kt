package com.example.webradioplayer.ui

import com.example.webradioplayer.model.IGenre

sealed class GenresUiState
data class GenresLoaded(val data: List<IGenre>) : GenresUiState()
data class GenresLoading(val isLoading: Boolean) : GenresUiState()
data class GenresLoadError(val message: String?) : GenresUiState()