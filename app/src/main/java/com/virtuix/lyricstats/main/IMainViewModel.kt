package com.virtuix.lyricstats.main


interface IMainViewModel {
	fun updateArtist(artist: String)
	fun updateSongTitle(songTitle: String)
	fun updateIsInvalidInput(isInvalidInput: Boolean)
	fun lookUpAndProcessLyrics()
	fun clearWords()
}