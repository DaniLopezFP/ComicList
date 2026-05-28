package com.example.mycomiclist.domain.repository

import androidx.lifecycle.LiveData
import com.example.mycomiclist.domain.model.Comic

interface ComicRepository {
    fun getAllComics(): LiveData<List<Comic>>
    fun toggleComicReadStatus(id: String)
}