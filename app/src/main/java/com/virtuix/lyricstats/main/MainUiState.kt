package com.virtuix.lyricstats.main

enum class ProcessOption {
	LONGEST, MOST_USED
}

data class MainUiState(
	val artist: String = "",
	val songTitle: String = "",
	val longestWord: String = "",
	val mostUsedWord: String = "",
	val isInvalidInput: Boolean = false
)