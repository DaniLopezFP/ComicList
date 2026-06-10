package com.example.mycomiclist.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mycomiclist.domain.model.Comic
import com.example.mycomiclist.domain.repository.ComicRepository

class ComicRepositoryImpl : ComicRepository {

    // Lista en memoria con unos cómics de prueba
    private val comicList = mutableListOf(
        Comic(
            "1",
            "The Amazing Spider-Man",
            "Stan Lee",
            1,
            "https://cdn.marvel.com/u/prod/marvel/i/mg/6/f0/68c053ab8d985/standard_incredible.jpg",
            false
        ),
        Comic(
            "2",
            "Batman the long halloween",
            "Jeph Loeb",
            1,
            "https://static.dc.com/dc/files/default_images/1283_900x1350.jpg?w=640",
            false
        ),
        Comic(
            "3",
            "Watchmen",
            "Alan Moore",
            1,
            "https://static.dc.com/dc/files/default_images/1462_400x600.jpg?w=640",
            false
        ),
        Comic(
            "4",
            "X-Men: M dinasty",
            "Brian Michael Bendis",
            1,
            "https://www.normacomics.com/media/catalog/product/cache/0d53bfb8e6abd9c2bc6a754fde669403/d/i/dinastia-m-marvel-must-have.jpg",
            false
        ),
        Comic(
            "5",
            "Daredevil: Born Again",
            "Frank Miller",
            1,
            "https://www.normacomics.com/media/catalog/product/cache/0d53bfb8e6abd9c2bc6a754fde669403/d/a/darevil-marvel-essentials.jpg",
            false
        ),
        Comic(
            "6",
            "Absolute Batman",
            "Frank Tieri",
            1,
            "https://www.normacomics.com/media/catalog/product/cache/0d53bfb8e6abd9c2bc6a754fde669403/b/a/batman-especial-arca-m.jpg",
            false
        )
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