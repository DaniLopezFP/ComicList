package com.example.mycomiclist.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mycomiclist.data.firestore.FirebaseComicsRepository
import com.example.mycomiclist.domain.model.Comic
import com.example.mycomiclist.domain.repository.ComicRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

/*
class ComicRepositoryImpl(
    private val firestore: FirebaseFirestore
) : ComicRepository {*/

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
/*
    private val _comicsLiveData = MutableLiveData<List<Comic>>(comicList)

    //override fun getAllComics(): LiveData<List<Comic>> = _comicsLiveData

    override fun getAllComics(userId: String): Flow<List<Comic>> {
        // ... tu código de retorno de Firestore o memoria ...
        return firestore.collection("users")
            .document(userId)
            .collection("comics")
            .snapshots()
            .map { snapshot -> snapshot.toObjects(Comic::class.java) }
    }

    /* Función anterior
    override fun toggleComicReadStatus(id: String) {
        val index = comicList.indexOfFirst { it.id == id }
        if (index != -1) {
            val current = comicList[index]
            comicList[index] = current.copy(isRead = !current.isRead)
            // Notificamos el cambio a los observadores
            _comicsLiveData.value = ArrayList(comicList)
        }
     */

    fun toggleComicReadStatus(id: String) {
        // Puedes dejarla vacía o borrarla por completo si ya no la usas en tus pantallas
    }

    override suspend fun updateComic(userId: String, comic: Comic) {
        firestore.collection("users")
            .document(userId)
            .collection("comics")
            .document(comic.id)
            .set(comic)
            .await()
    }
}*/