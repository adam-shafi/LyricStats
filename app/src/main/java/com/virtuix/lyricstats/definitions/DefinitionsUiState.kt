package com.virtuix.lyricstats.definitions

import com.virtuix.lyricstats.api.DictionaryResponse
import com.virtuix.lyricstats.main.ProcessOption

data class DefinitionsUiState(
    val longestWord: String = "",
    val mostUsedWord: String = "",
    val processOption: ProcessOption = ProcessOption.LONGEST,
    val longestWordDefinition: DictionaryResponse? = null,
    val mostUsedWordDefinition: DictionaryResponse? = null,
    val isLoading: Boolean = true,
)

data class DisplayedDefinitionState(
    val displayedWordDefinition: DictionaryResponse?,
    val isDisplayedError: Boolean = false,
    val displayedWord: String
)