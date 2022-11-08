package com.example.webradioplayer.ui

import com.example.webradioplayer.model.IPlaylist

sealed class PlaylistUiState

data class PlaylistSelected(val genre: IPlaylist) : PlaylistUiState()
data class PlaylistLoaded(val data: List<IPlaylist>) : PlaylistUiState()
data class PlaylistLoading(val isLoading: Boolean) : PlaylistUiState()
data class PlaylistLoadError(val message: String?) : PlaylistUiState()