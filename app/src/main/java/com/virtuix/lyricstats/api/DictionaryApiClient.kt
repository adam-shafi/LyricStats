package com.virtuix.lyricstats.api

object DictionaryApiClient {
    val dictionaryApi: DictionaryApi by lazy {
        RetrofitClient.dictionaryRetrofit.create(DictionaryApi::class.java)
    }
}