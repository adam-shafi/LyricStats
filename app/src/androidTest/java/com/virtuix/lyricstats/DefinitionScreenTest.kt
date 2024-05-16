package com.virtuix.lyricstats

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import com.virtuix.lyricstats.definitions.DefinitionsScreen
import com.virtuix.lyricstats.definitions.DefinitionsUiState
import com.virtuix.lyricstats.definitions.DisplayedDefinitionState
import com.virtuix.lyricstats.definitions.IDefinitionsViewModel
import com.virtuix.lyricstats.main.ProcessOption
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DefinitionScreenTest {
    companion object {
        private const val LONGEST_WORD = "everything"
        private const val MOST_USED_WORD = "i'm"
    }

    @get:Rule
    val testRule = createAndroidComposeRule<ComponentActivity>()
    private val viewModel = mock<IDefinitionsViewModel>()
    private val uiState =
        DefinitionsUiState(longestWord = LONGEST_WORD, mostUsedWord = MOST_USED_WORD)
    private val navController = mock<NavHostController>()

    @Test
    fun userCanSwapBetweenLyricOptions() {
        testRule.setContent {
            DefinitionsScreen.DefinitionsScreen(
                viewModel = viewModel,
                uiState = uiState
            )
        }

        testRule.onNodeWithTag(testRule.activity.getString(R.string.most_used_lyric))
            .performClick()
        verify(viewModel).updateProcessOption(ProcessOption.MOST_USED.ordinal)

        testRule.onNodeWithTag(testRule.activity.getString(R.string.longest_lyric))
            .performClick()
        verify(viewModel).updateProcessOption(ProcessOption.LONGEST.ordinal)
    }

    @Test
    fun userCanNavigateBack() {
        testRule.setContent {
            DefinitionsScreen.Screen(
                viewModel = viewModel,
                uiState = uiState,
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }

        testRule.onNodeWithTag(testRule.activity.getString(R.string.navigate_back))
            .performClick()

        verify(navController).navigateUp()
    }

    @Test
    fun userCanSeeLongestWord() {
        testRule.setContent {
            DefinitionsScreen.DefinitionError(
                displayedDefinitionState = DisplayedDefinitionState(
                    displayedWordDefinition = null,
                    isDisplayedError = true,
                    displayedWord = LONGEST_WORD
                )
            )
        }

        testRule.onNodeWithText(LONGEST_WORD).assertExists()
    }

    @Test
    fun userCanSeeMostUsedWord() {
        testRule.setContent {
            DefinitionsScreen.DefinitionError(
                displayedDefinitionState = DisplayedDefinitionState(
                    displayedWordDefinition = null,
                    isDisplayedError = true,
                    displayedWord = MOST_USED_WORD
                )
            )
        }

        testRule.onNodeWithText(MOST_USED_WORD).assertExists()
    }
}