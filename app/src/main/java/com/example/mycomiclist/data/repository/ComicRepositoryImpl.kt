package com.example.mycomiclist.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mycomiclist.domain.model.Comic
import com.example.mycomiclist.domain.repository.ComicRepository

class ComicRepositoryImpl : ComicRepository {

    // Lista en memoria con unos cómics de prueba
    private val comicList = mutableListOf(
        Comic("1", "The Amazing Spider-Man", "Stan Lee", 1, "", true),
        Comic("2", "Batman: Year One", "Frank Miller", 1, "", false),
        Comic("3", "Watchmen", "Alan Moore", 1, "", false)
    )

    private val _comicsLiveData = MutableLiveData<List<Comic>>(comicList)

    override fun getAllComics(): LiveData<List<Comic>> = _comicsLiveData

    override fun toggleComicReadStatus(id: String) {
        val index = comicList.indexOfFirst { it.id == id }
        if (index != -1) {
            val current = comicList[index]
            comicList[index] = current.copy(isRead = !current.isRead)
            // Notificamos el cambio a los observadores
            _comicsLiveData.value = ArrayList(comicList)
        }
    }
}