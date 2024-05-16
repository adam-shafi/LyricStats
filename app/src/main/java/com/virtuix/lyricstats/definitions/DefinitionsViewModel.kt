package com.virtuix.lyricstats.definitions

import android.util.Log
import androidx.lifecycle.ViewModel
import com.virtuix.lyricstats.api.DictionaryApi
import com.virtuix.lyricstats.api.DictionaryApiClient
import com.virtuix.lyricstats.api.DictionaryResponse
import com.virtuix.lyricstats.main.ProcessOption
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DefinitionsViewModel(
    longestWord: String,
    mostUsedWord: String,
    private val dictionaryApi: DictionaryApi = DictionaryApiClient.dictionaryApi,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel(), IDefinitionsViewModel {
    companion object {
        private const val TAG = "DefinitionsViewModel"
    }

    private val ioScope = CoroutineScope(ioDispatcher)
    private val _uiState = MutableStateFlow(
        DefinitionsUiState(
            longestWord = longestWord,
            mostUsedWord = mostUsedWord
        )
    )
    val uiState: StateFlow<DefinitionsUiState> = _uiState.asStateFlow()

    init {
        updateIsLoading(isLoading = true)
        Log.d(TAG, "Longest Word: $longestWord")
        Log.d(TAG, "Most Used Word: $mostUsedWord")
        lookupLongestWordDefinition()
        lookupMostUsedWordDefinition()
    }

    override fun updateIsLoading(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading) }
    }

    override fun updateProcessOption(processOption: Int) {
        _uiState.update { it.copy(processOption = ProcessOption.values()[processOption]) }
    }

    override fun isSelectedOption(index: Int): Boolean =
        _uiState.value.processOption.ordinal == index

    private fun lookupLongestWordDefinition() {
        ioScope.launch {
            updateIsLoading(isLoading = true)
            try {
                val longestWordDefinition = dictionaryApi
                    .dictionary(word = _uiState.value.longestWord)
                    .first()
                updateLongestWordDefinition(longestWordDefinition = longestWordDefinition)
                updateIsLoading(isLoading = false)
            } catch (e: Exception) {
                Log.d(TAG, "Longest Word Definition call failed: ${e.message}")
            }
        }
    }

    private fun updateLongestWordDefinition(longestWordDefinition: DictionaryResponse) {
        _uiState.update {
            it.copy(longestWordDefinition = longestWordDefinition)
        }
        Log.d(TAG, "Longest Word Definition: $longestWordDefinition")
        Log.d(TAG, "Longest Word Definition from uiState: ${_uiState.value.longestWordDefinition}")
    }

    private fun lookupMostUsedWordDefinition() {
        ioScope.launch {
            updateIsLoading(isLoading = true)
            try {
                val mostUsedWordDefinition = dictionaryApi
                    .dictionary(word = _uiState.value.mostUsedWord)
                    .first()
                updateMostUsedDefinition(mostUsedWordDefinition = mostUsedWordDefinition)
                updateIsLoading(isLoading = false)
            } catch (e: Exception) {
                Log.d(TAG, "Most Used Definition call failed: ${e.message}")
            }
        }
    }

    private fun updateMostUsedDefinition(mostUsedWordDefinition: DictionaryResponse) {
        _uiState.update {
            it.copy(mostUsedWordDefinition = mostUsedWordDefinition)
        }
        Log.d(TAG, "Most Used Definition: $mostUsedWordDefinition")
        Log.d(TAG, "Most Used Definition from uiState: ${_uiState.value.mostUsedWordDefinition}")
    }

}