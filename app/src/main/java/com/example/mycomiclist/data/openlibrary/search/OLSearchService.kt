package com.example.mycomiclist.data.openlibrary.search

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://openlibrary.org/" // TLD base (Pág 62)

// Configuración del motor JSON tolerante a nulos y campos desconocidos (Pág 62)
val apiJson = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

private val olSearchRetrofit = Retrofit.Builder()
    .addConverterFactory(apiJson.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface OLSearchService {
    @GET("search.json") // Endpoint de búsqueda (Pág 62)
    suspend fun getBookInfoByIsbn(@Query("isbn") isbn: String): BookSearch
}

object OLSearchApi {
    val searchService: OLSearchService by lazy {
        olSearchRetrofit.create(OLSearchService::class.java)
    }
}