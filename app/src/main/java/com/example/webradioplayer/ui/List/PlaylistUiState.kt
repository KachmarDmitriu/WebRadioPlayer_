package com.example.webradioplayer.ui

import com.example.webradioplayer.model.IGenre

sealed class PlaylistUiState
data class GenreSelected(val genre: IGenre) : GenresUiState()