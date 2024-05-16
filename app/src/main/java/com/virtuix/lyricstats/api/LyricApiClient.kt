package com.virtuix.lyricstats.api

object LyricApiClient {
	val lyricApi: LyricApi by lazy {
		RetrofitClient.lyricsRetrofit.create(LyricApi::class.java)
	}
}