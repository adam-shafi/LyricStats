package com.virtuix.lyricstats.definitions

interface IDefinitionsViewModel {
    fun updateProcessOption(processOption: Int)
    fun updateIsLoading(isLoading: Boolean)
    fun isSelectedOption(index: Int) : Boolean
}