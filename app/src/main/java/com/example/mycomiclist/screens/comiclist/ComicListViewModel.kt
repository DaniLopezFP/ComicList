package com.example.mycomiclist.screens.comiclist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.mycomiclist.data.repository.ComicRepositoryImpl
import com.example.mycomiclist.domain.model.Comic
import com.example.mycomiclist.domain.repository.ComicRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ComicListViewModel(
    private val repository: ComicRepository,
    val userName: String,
    private val userId: String,
) : ViewModel() {

    // Instanciamos el repositorio (en el futuro se usaría inyección de dependencias)
    //private val repository: ComicRepository = ComicRepositoryImpl()

    // Exponemos el LiveData de cómics del repositorio a la vista
    //val comics: LiveData<List<Comic>> = repository.getAllComics()
    val comicList: StateFlow<List<Comic>> = repository.getAllComics(userId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Lógica para cambiar el estado de lectura de un cómic
    /*fun toggleReadStatus(comicId: String) {
        repository.toggleComicReadStatus(comicId)
    }*/
    fun toggleComicReadStatus(userId: String, comic: Comic) {
        // Lanzamos una corrutina porque hablar con Firestore es una operación 'suspend'
        viewModelScope.launch {
            // 1. Creamos una copia del cómic invirtiendo el booleano 'isRead'
            val updatedComic = comic.copy(isRead = !comic.isRead)

            // 2. Llamamos al repositorio de Firestore para que actualice el documento en la nube
            repository.updateComic(userId, updatedComic)
        }
    }
}