package com.virtuix.lyricstats

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavHostController
import com.virtuix.lyricstats.main.IMainViewModel
import com.virtuix.lyricstats.main.MainScreen
import com.virtuix.lyricstats.main.MainUiState
import com.virtuix.lyricstats.navigation.Screen
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class MainScreenTest {
    companion object {
        private const val ARTIST = "Nine Inch Nails"
        private const val SONG_TITLE = "The Only Time"
    }

    @get:Rule
    val testRule = createAndroidComposeRule<ComponentActivity>()
    private val viewModel = mock<IMainViewModel>()
    private val uiState = MainUiState()
    private val navController = mock<NavHostController>()

    @Test
    fun userCanInputArtist() {
        testRule.setContent {
            MainScreen.Screen(
                viewModel = viewModel,
                uiState = uiState,
                onProcessLyrics = { longestWord, mostUsedWord ->
                    navController.navigate(
                        Screen.DefinitionsScreen.createRoute(longestWord, mostUsedWord)
                    )
                }
            )
        }

        testRule.onNodeWithText(testRule.activity.getString(R.string.artist))
            .performTextInput(ARTIST)

        verify(viewModel).updateArtist(ARTIST)
    }

    @Test
    fun userCanInputSongTitle() {
        testRule.setContent {
            MainScreen.Screen(
                viewModel = viewModel,
                uiState = uiState,
                onProcessLyrics = { longestWord, mostUsedWord ->
                    navController.navigate(
                        Screen.DefinitionsScreen.createRoute(longestWord, mostUsedWord)
                    )
                }
            )
        }

        testRule.onNodeWithText(testRule.activity.getString(R.string.song_title))
            .performTextInput(SONG_TITLE)

        verify(viewModel).updateSongTitle(SONG_TITLE)
    }

    @Test
    fun userProcessLyrics() {
        testRule.setContent {
            MainScreen.Screen(
                viewModel = viewModel,
                uiState = uiState,
                onProcessLyrics = { longestWord, mostUsedWord ->
                    navController.navigate(
                        Screen.DefinitionsScreen.createRoute(longestWord, mostUsedWord)
                    )
                }
            )
        }

        testRule.onNodeWithText(testRule.activity.getString(R.string.look_up_and_process_lyrics))
            .performClick()

        verify(viewModel).lookUpAndProcessLyrics()
    }

    @Test
    fun userDismissInvalidInputDialog() {
        testRule.setContent {
            MainScreen.Screen(
                viewModel = viewModel,
                uiState = MainUiState(isInvalidInput = true),
                onProcessLyrics = { longestWord, mostUsedWord ->
                    navController.navigate(
                        Screen.DefinitionsScreen.createRoute(longestWord, mostUsedWord)
                    )
                }
            )
        }
        testRule.onNodeWithText(testRule.activity.getString(R.string.ok))
            .performClick()

        verify(viewModel).updateIsInvalidInput(false)
    }
}