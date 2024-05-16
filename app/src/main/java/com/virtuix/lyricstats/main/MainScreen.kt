package com.virtuix.lyricstats.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.virtuix.lyricstats.R
import com.virtuix.lyricstats.ui.theme.LyricStatsTheme


@OptIn(ExperimentalMaterial3Api::class)
object MainScreen {
    @Composable
    fun Screen(
        viewModel: IMainViewModel,
        uiState: MainUiState,
        onProcessLyrics: (longestWord: String, mostUsedWord: String) -> Unit
    ) {
        LaunchedEffect(key1 = uiState.longestWord, key2 = uiState.mostUsedWord) {
            if (uiState.longestWord.isNotBlank() && uiState.mostUsedWord.isNotBlank()) {
                onProcessLyrics(uiState.longestWord, uiState.mostUsedWord)
                viewModel.clearWords()
            }
        }
        LyricStatsTheme {
            Scaffold(
                topBar = {
                    TopAppBar(title = {
                        Text(text = stringResource(R.string.lyric_stats))
                    })
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(all = 8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    InputFields(uiState = uiState, viewModel = viewModel)
                    Button(
                        onClick = {
                            viewModel.lookUpAndProcessLyrics()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 8.dp)
                    ) {
                        Text(text = stringResource(id = R.string.look_up_and_process_lyrics))
                    }
                    if (uiState.isInvalidInput) {
                        AlertDialog(
                            title = { Text(text = stringResource(id = R.string.invalid_input)) },
                            text = { Text(text = stringResource(id = R.string.invalid_input_description)) },
                            icon = {
                                Icon(
                                    imageVector = Icons.Filled.Warning,
                                    contentDescription = stringResource(id = R.string.invalid_input)
                                )
                            },
                            onDismissRequest = { viewModel.updateIsInvalidInput(false) },
                            confirmButton = {
                                TextButton(onClick = { viewModel.updateIsInvalidInput(false) }) {
                                    Text(text = stringResource(id = R.string.ok))
                                }
                            }
                        )
                    }
                }
            }

        }


    }

    @Composable
    fun InputFields(uiState: MainUiState, viewModel: IMainViewModel) {
        Column {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp),
                text = stringResource(id = R.string.lyric_stats_description)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp),
                value = uiState.artist,
                onValueChange = viewModel::updateArtist,
                placeholder = { Text(text = stringResource(id = R.string.artist)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                )
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp),
                value = uiState.songTitle,
                onValueChange = viewModel::updateSongTitle,
                placeholder = { Text(text = stringResource(id = R.string.song_title)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        viewModel.lookUpAndProcessLyrics()
                    }
                )
            )
        }
    }
}