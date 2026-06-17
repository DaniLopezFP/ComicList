package com.example.mycomiclist.domain.repository

import com.example.mycomiclist.domain.model.Comic

interface SearchRepository {
    // Recibe un ISBN y devuelve un objeto Comic con los datos mapeados (Pág 61)
    suspend fun getComicInfoByIsbn(isbn: String): Comic
}