package com.virtuix.lyricstats.definitions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.virtuix.lyricstats.R
import com.virtuix.lyricstats.api.Definitions
import com.virtuix.lyricstats.main.ProcessOption

object DefinitionsScreen {

    @Composable
    fun rememberDisplayedDefinitionState(uiState: DefinitionsUiState) =
        remember(key1 = uiState.processOption) {
            when (uiState.processOption) {
                ProcessOption.LONGEST -> {
                    DisplayedDefinitionState(
                        displayedWordDefinition = uiState.longestWordDefinition,
                        isDisplayedError = uiState.longestWordDefinition == null,
                        displayedWord = uiState.longestWord
                    )
                }

                ProcessOption.MOST_USED -> {
                    DisplayedDefinitionState(
                        displayedWordDefinition = uiState.mostUsedWordDefinition,
                        isDisplayedError = uiState.mostUsedWordDefinition == null,
                        displayedWord = uiState.mostUsedWord
                    )
                }
            }
        }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Screen(
        viewModel: IDefinitionsViewModel,
        uiState: DefinitionsUiState,
        onBackClick: () -> Unit
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.lyric_definitions)) },
                    navigationIcon = {
                        IconButton(
                            modifier = Modifier.testTag(tag = stringResource(id = R.string.navigate_back)),
                            onClick = onBackClick
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(id = R.string.navigate_back)
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            val modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
            if (uiState.isLoading) {
                LoadingScreen(modifier = modifier)
            } else {
                DefinitionsScreen(modifier = modifier, uiState = uiState, viewModel = viewModel)
            }
        }

    }

    @Composable
    fun LoadingScreen(modifier: Modifier) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DefinitionsScreen(
        modifier: Modifier = Modifier,
        uiState: DefinitionsUiState,
        viewModel: IDefinitionsViewModel
    ) {
        val displayedDefinitionState = rememberDisplayedDefinitionState(uiState = uiState)
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp)
            ) {
                val processWordOptions = stringArrayResource(id = R.array.lyric_types)
                processWordOptions.forEachIndexed { index, label ->
                    SegmentedButton(
                        modifier = Modifier.testTag(tag = label),
                        selected = viewModel.isSelectedOption(index),
                        onClick = {
                            viewModel.updateProcessOption(index)
                        },
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = processWordOptions.size
                        )
                    ) {
                        Text(label)
                    }
                }
            }
            if (displayedDefinitionState.isDisplayedError) {
                DefinitionError(displayedDefinitionState = displayedDefinitionState)
            } else {
                DefinitionsList(displayedDefinitionState = displayedDefinitionState)
            }


        }
    }

    @Composable
    fun DefinitionError(displayedDefinitionState: DisplayedDefinitionState) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = displayedDefinitionState.displayedWord)
            Spacer(modifier = Modifier.height(10.dp))
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = stringResource(R.string.word_error_description)
            )
            Text(text = stringResource(R.string.word_error_description))
        }
    }

    @Composable
    fun DefinitionsList(displayedDefinitionState: DisplayedDefinitionState) {
        displayedDefinitionState.displayedWordDefinition?.let { definition ->
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Column {
                        Text(text = definition.word)
                        definition.phonetic?.let { Text(text = it) }
                    }
                }

                items(definition.meanings) { definitions ->
                    Definition(definitions = definitions)
                }
            }
        }

    }

    @Composable
    fun Definition(definitions: Definitions) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = definitions.partOfSpeech, fontStyle = FontStyle.Italic)
            definitions.definitions.forEachIndexed { index, definition ->
                Text(text = "${index + 1}. ${definition.definition}")
                definition.example?.let { Text(text = "\"$it\"", color = Color.Gray) }
                if (definition.synonyms.isNotEmpty()) {
                    Row {
                        Text(stringResource(id = R.string.synonyms))
                        definition.synonyms.forEach { synonym ->
                            Text(text = synonym)
                        }
                    }
                }
            }
        }
    }
}
