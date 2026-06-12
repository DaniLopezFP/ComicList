package com.example.mycomiclist.domain.repository

import androidx.lifecycle.LiveData
import com.example.mycomiclist.domain.model.Comic
import kotlinx.coroutines.flow.Flow

interface ComicRepository {
    //fun getAllComics(): LiveData<List<Comic>>
    //fun toggleComicReadStatus(id: String)
    fun getAllComics(userId: String): Flow<List<Comic>>

    suspend fun addComic(userId: String, book: Comic)
    suspend fun updateComic(userId: String, book: Comic)
    suspend fun deleteComic(userId: String, comicId: String)
}