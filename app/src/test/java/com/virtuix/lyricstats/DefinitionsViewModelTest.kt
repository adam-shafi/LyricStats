package com.virtuix.lyricstats

import com.virtuix.lyricstats.api.DictionaryApi
import com.virtuix.lyricstats.api.DictionaryResponse
import com.virtuix.lyricstats.definitions.DefinitionsViewModel
import com.virtuix.lyricstats.main.ProcessOption
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class DefinitionsViewModelTest {
    companion object {
        private const val LONGEST_WORD = "everything"
        private const val MOST_USED_WORD = "i'm"
        private val MOST_USED_PROCESS_OPTION = ProcessOption.MOST_USED
        private val LONGEST_PROCESS_OPTION = ProcessOption.LONGEST
    }

    private val dictionaryApi = mock<DictionaryApi>()
    private val response = mock<List<DictionaryResponse>>()
    private val subject = DefinitionsViewModel(
        longestWord = LONGEST_WORD,
        mostUsedWord = MOST_USED_WORD,
        dictionaryApi = dictionaryApi
    )

    @Before
    fun setup() {
        dictionaryApi.stub {
            onBlocking { dictionary(word = LONGEST_WORD) }.doReturn(response)
        }
    }

    @Test
    fun `updateIsLoading() updates the is loading flag`() {
        subject.updateIsLoading(isLoading = true)
        Assert.assertTrue(subject.uiState.value.isLoading)

        subject.updateIsLoading(isLoading = false)
        Assert.assertFalse(subject.uiState.value.isLoading)
    }

    @Test
    fun `updateProcessOption() updates the lyric being defined`() {
        Assert.assertEquals(LONGEST_PROCESS_OPTION, subject.uiState.value.processOption)

        subject.updateProcessOption(processOption = MOST_USED_PROCESS_OPTION.ordinal)

        Assert.assertEquals(MOST_USED_PROCESS_OPTION, subject.uiState.value.processOption)

        subject.updateProcessOption(processOption = LONGEST_PROCESS_OPTION.ordinal)

        Assert.assertEquals(LONGEST_PROCESS_OPTION, subject.uiState.value.processOption)
    }

    @Test
    fun `isSelectedOptions() returns whether the index passed is selected`() {
        Assert.assertTrue(subject.isSelectedOption(index = LONGEST_PROCESS_OPTION.ordinal))
        Assert.assertFalse(subject.isSelectedOption(index = MOST_USED_PROCESS_OPTION.ordinal))

        subject.updateProcessOption(processOption = MOST_USED_PROCESS_OPTION.ordinal)

        Assert.assertFalse(subject.isSelectedOption(index = LONGEST_PROCESS_OPTION.ordinal))
        Assert.assertTrue(subject.isSelectedOption(index = MOST_USED_PROCESS_OPTION.ordinal))
    }



    @Test
    fun `init() looks up the longest and most used words`() =
        runTest {
           DefinitionsViewModel(
                longestWord = LONGEST_WORD,
                mostUsedWord = MOST_USED_WORD,
                dictionaryApi = dictionaryApi,
                ioDispatcher = StandardTestDispatcher(testScheduler)
            )

            advanceUntilIdle()

            verify(dictionaryApi, times(2)).dictionary(
                word = MOST_USED_WORD
            )
        }

    @Test
    fun `lookupMostUsedWordDefinition() retrieves an empty list when no valid definition exists`() =
        runTest {
            val subject = DefinitionsViewModel(
                longestWord = LONGEST_WORD,
                mostUsedWord = MOST_USED_WORD,
                dictionaryApi = dictionaryApi,
                ioDispatcher = StandardTestDispatcher(testScheduler)
            )
            Assert.assertTrue(subject.uiState.value.isLoading)

            advanceUntilIdle()
            Assert.assertNull(subject.uiState.value.mostUsedWordDefinition)
        }
}