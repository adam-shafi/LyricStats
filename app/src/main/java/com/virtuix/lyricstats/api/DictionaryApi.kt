package com.virtuix.lyricstats.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {

    @GET("{word}")
    suspend fun dictionary(@Path("word") word: String): List<DictionaryResponse>
}