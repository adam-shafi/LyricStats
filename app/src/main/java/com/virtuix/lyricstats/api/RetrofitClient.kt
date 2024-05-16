package com.virtuix.lyricstats.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
	private const val LYRICS_BASE_URL = "https://api.lyrics.ovh/v1/"
	private const val DICTIONARY_BASE_URL = "https://api.dictionaryapi.dev/api/v2/entries/en/"

	val lyricsRetrofit: Retrofit by lazy {
		Retrofit.Builder()
			.baseUrl(LYRICS_BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
	}

	val dictionaryRetrofit: Retrofit by lazy {
		Retrofit.Builder()
			.baseUrl(DICTIONARY_BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
	}
}