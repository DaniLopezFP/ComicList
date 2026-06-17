package com.example.mycomiclist.data.openlibrary.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookSearch(
    val docs: List<BookInfo> // Almacena el array de resultados de la API (Pág 60)
)

@Serializable
data class BookInfo(
    val title: String = "",
    @SerialName("author_name") val authorName: List<String> = emptyList(),
    @SerialName("first_publish_year") val firstPublishYear: Int = 0,
    @SerialName("cover_i") val coverId: Int? = null // El ID único de portada (Pág 60)
)