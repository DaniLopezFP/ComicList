package com.example.mycomiclist.domain.openlibrary

import com.example.mycomiclist.data.openlibrary.search.BookInfo
import com.example.mycomiclist.data.openlibrary.search.OLSearchService
import com.example.mycomiclist.domain.model.Comic
import com.example.mycomiclist.domain.repository.SearchRepository

class OLSearchRepository(private val apiService: OLSearchService) : SearchRepository {

    override suspend fun getComicInfoByIsbn(isbn: String): Comic {
        // Hacemos la llamada HTTP a la API y cogemos el primer documento coincidente (Pág 62)
        val response = apiService.getBookInfoByIsbn(isbn)
        return response.docs.first().toComic(isbn)
    }
}

// 🌟 FUNCIÓN DE EXTENSIÓN: Transforma BookInfo de la API al Comic de tu App (Pág 63)
fun BookInfo.toComic(isbn: String) = Comic(
    id = "", // Firebase le asignará el ID al guardarlo
    title = this.title,
    author = this.authorName.joinToString(", "), // Une los autores por comas (Pág 668)
    volumeNumber = 1,
    imageUrl = if (this.coverId != null) "https://covers.openlibrary.org/b/id/${this.coverId}.jpg" else "", // Genera la URL nativa (Pág 63)
    isRead = false
)