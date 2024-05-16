package com.virtuix.lyricstats.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.virtuix.lyricstats.api.LyricApi
import com.virtuix.lyricstats.api.LyricApiClient
import com.virtuix.lyricstats.data.Grammar
import com.virtuix.lyricstats.definitions.DefinitionsViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val lyricApi: LyricApi = LyricApiClient.lyricApi,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel(), IMainViewModel {

    companion object {
        private const val TAG = "MainViewModel"
    }

    private val ioScope = CoroutineScope(ioDispatcher)
    private val _uiState = MutableStateFlow(
        MainUiState(
            artist = "",
            songTitle = ""
        )
    )
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    override fun updateArtist(artist: String) {
        _uiState.update { it.copy(artist = artist) }
    }

    override fun updateSongTitle(songTitle: String) {
        _uiState.update { it.copy(songTitle = songTitle) }
    }

    override fun updateIsInvalidInput(isInvalidInput: Boolean) {
        if (isInvalidInput) {
            Log.d(
                TAG,
                "Invalid Input with artist: ${_uiState.value.artist} and song: ${_uiState.value.songTitle}"
            )
        }
        _uiState.update { it.copy(isInvalidInput = isInvalidInput) }
    }

    override fun lookUpAndProcessLyrics() {
        ioScope.launch {
            try{
                val response = lyricApi.lyrics(
                    artist = _uiState.value.artist,
                    songTitle = _uiState.value.songTitle
                )
                val lyrics = response.lyrics
                filterAndProcessLyrics(lyrics)
            }
            catch(e: Exception) {
                Log.d(TAG, "Look Up and Process Lyrics call failed: ${e.message}")
                updateIsInvalidInput(true)
            }
        }
    }


    override fun clearWords() {
        _uiState.update {
            it.copy(
                longestWord = "",
                mostUsedWord = ""
            )
        }
    }

    private fun filterAndProcessLyrics(lyrics: String) {
        Log.d(TAG, "Raw lyrics: $lyrics")
        val filteredLyrics = filterLyrics(lyrics)
        Log.d(TAG, "Filtered lyrics: $filteredLyrics")
        findLongestWord(filteredLyrics)
        findMostUsedWord(filteredLyrics)
    }

    private fun filterLyrics(lyrics: String): List<String> {
        return lyrics
            .split("\\s+".toRegex())
            .filter { lyric -> Grammar.ARTICLES.contains(lyric).not() }
            .filter { lyric -> Grammar.PROPOSITIONS.contains(lyric).not() }
            .filter { lyric -> Grammar.INTERJECTIONS.contains(lyric).not() }
    }

    private fun findLongestWord(lyrics: List<String>) {
        val longestWord = lyrics.reduce { longest, current ->
            if (current.length > longest.length) current else longest
        }
        _uiState.update { it.copy(longestWord = longestWord) }
    }

    private fun findMostUsedWord(lyrics: List<String>) {
        val mostUsedWord = lyrics
            .groupBy { it }
            .maxBy { it.value.size }
            .key
        _uiState.update { it.copy(mostUsedWord = mostUsedWord) }
    }

}
